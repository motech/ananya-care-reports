package org.motechproject.carereporting.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.UserService;
import org.motechproject.carereporting.utils.configuration.ConfigurationLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CareReportingAuthenticationProvider implements AuthenticationProvider {

    private static final Integer SUPER_USER_AREA_ID = 1;
    private static final String SUPER_USER_NAME = "Super user";
    private static final String SUPER_USER_ROLE = "Admin";
    private static final String SUPER_USER_READ_ONLY_ROLE = "Read Only";

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    private static Properties commCareConfiguration;

    static {
        try {
            commCareConfiguration = ConfigurationLocator.getCommCareConfiguration();
        } catch (IOException e) {
            throw new CareRuntimeException(e);
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        final String login = prepareCommCareLogin(authentication);
        final String password = ((String) authentication.getCredentials()).trim();
        String commCareDomain = getCommcareProperty("commcare.authentication.domain");
        String commCareUrl = getCommcareProperty("commcare.authentication.url", commCareDomain);

        if (isSuperUserEnabled() && isSuperUser(login, password)) {
            return createSuperUserAuthentication();
        }

        try {
            RestTemplate restTemplate = new RestTemplate(createSecureTransport(login, password));
            MultiValueMap commCareAuthenticationData = new LinkedMultiValueMap<String, String>() { {
                add("username", login);
                add("password", password);
            } };
            String userJson = restTemplate.postForObject(commCareUrl, commCareAuthenticationData, String.class);
            Map<String, Object> result = new ObjectMapper().readValue(userJson, Map.class);
            UserEntity user = prepareCommCareUser(result);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        } catch (IOException e) {
            throw new CareRuntimeException(e);
        } catch (Exception e) {
            throw new BadCredentialsException("Bad CommCare username / password!", e);
        }
    }

    private String prepareCommCareLogin(Authentication authentication) {
        String login = ((String) authentication.getPrincipal()).trim();
        if (!login.contains("@")) {
            login += prepareMobileWorkerEmailDomain();
        }
        return login;
    }

    private String prepareMobileWorkerEmailDomain() {
        return "@" +
                getCommcareProperty("commcare.authentication.domain") +
                "." +
                getCommcareProperty("commcare.authentication.host");
    }

    private boolean isSuperUserEnabled() {
        return Boolean.valueOf(getCommcareProperty("commcare.superuser.enabled"));
    }

    private boolean isSuperUser(String login, String password) {
        String superUserLogin = getCommcareProperty("commcare.superuser.login");
        String superUserPassword = getCommcareProperty("commcare.superuser.password");
        return login.equals(superUserLogin) && password.equals(superUserPassword);
    }

    private Authentication createSuperUserAuthentication() {
        UserEntity mockSuperUser = prepareMockSuperUser();
        return new UsernamePasswordAuthenticationToken(mockSuperUser, null, mockSuperUser.getAuthorities());
    }

    private UserEntity prepareMockSuperUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Integer.MAX_VALUE);
        userEntity.setRoles(userService.getAllRoles());
        userEntity.setArea(areaService.getAreaById(SUPER_USER_AREA_ID));
        userEntity.setUsername(SUPER_USER_NAME);
        return userEntity;
    }

    private UserEntity prepareCommCareUser(Map<String, Object> apiResult) {
        Map<String, String> userData = (Map<String, String>) apiResult.get("user_data");

        String userName = (String) apiResult.get("username");
        Set<RoleEntity> roles = getUserRoles(apiResult);
        AreaEntity area = getUserArea(userData);

        return prepareCareReportingUser(userName, area, roles);
    }

    private UserEntity prepareCareReportingUser(String userName, AreaEntity area, Set<RoleEntity> roles) {
        UserEntity user;
        if (!userService.doesUserExist(userName)) {
            user = userService.register(userName, area, roles);
        } else {
            user = userService.getUserByName(userName);
            updateUserAreaAndRoles(user, area, roles);
        }
        return user;
    }

    private void updateUserAreaAndRoles(UserEntity user, AreaEntity area, Set<RoleEntity> roles) {
        if (!user.getRoles().containsAll(roles) || !user.getArea().equals(area)) {
            user.setRoles(roles);
            user.setArea(area);
            userService.updateUser(user);
        }
    }

    private Set<RoleEntity> getUserRoles(Map<String, Object> apiResult) {
        Set<RoleEntity> roles;
        if (isMobileUser(apiResult)) {
            roles = getMobileUserRoles(apiResult);
        } else {
            roles = getWebUserRoles(apiResult);
        }
        return roles;
    }

    private boolean isMobileUser(Map<String, Object> apiResult) {
        return apiResult.get("user_data") != null;
    }

    private Set<RoleEntity> getMobileUserRoles(Map<String, Object> apiResult) {
        Set<RoleEntity> roles = new HashSet<>();
        Map<String, String> userData = (Map<String, String>) apiResult.get("user_data");
        roles.add(userService.getRoleByName(userData.get("reportview")));
        return roles;
    }

    private Set<RoleEntity> getWebUserRoles(Map<String, Object> apiResult) {
        Set<RoleEntity> roles = new HashSet<>();
        String[] commCareAdminRoles = commCareConfiguration.getProperty("commcare.superuser.roles").split(File.separator);
        String webUserRole = ((String) apiResult.get("role")).trim();
        if (Arrays.asList(commCareAdminRoles).contains(webUserRole)) {
            roles.add(userService.getRoleByName(SUPER_USER_ROLE));
        } else {
            roles.add(userService.getRoleByName(SUPER_USER_READ_ONLY_ROLE));
        }
        return roles;
    }

    private AreaEntity getUserArea(Map<String, String> userData) {
        if (userData == null) {
            return areaService.getAreaById(1);
        }
        String level = getUserLevel(userData);
        String area = userData.get(level);
        return areaService.getAreaOnLevel(area, level);
    }

    private String getUserLevel(Map<String, String> userData) {
        if (userData.get("block") != null && !userData.get("block").isEmpty()) {
            return "block";
        } else if (userData.get("district") != null && !userData.get("district").isEmpty()) {
            return "district";
        } else if (userData.get("state") != null && !userData.get("state").isEmpty()) {
            return "state";
        }
        return "national";
    }

    protected ClientHttpRequestFactory createSecureTransport(String username, String password) {
        HttpClient client = new HttpClient();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        String commCareHost = getCommcareProperty("commcare.authentication.host");
        String commCarePort = getCommcareProperty("commcare.authentication.port");
        client.getState().setCredentials(new AuthScope(commCareHost, Integer.valueOf(commCarePort)), credentials);
        return new CommonsClientHttpRequestFactory(client);
    }

    private String getCommcareProperty(String propertyName, String... args) {
        MessageFormat messageFormat = new MessageFormat(commCareConfiguration.getProperty(propertyName));
        return messageFormat.format(args);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
