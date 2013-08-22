package org.motechproject.carereporting.auth;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.codehaus.jackson.map.ObjectMapper;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.exception.EntityException;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CareReportingAuthenticationProvider implements AuthenticationProvider {

    private static final Integer SUPER_USER_AREA_ID = 1;
    private static final String SUPER_USER_NAME = "Super user";

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
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) {
        Map<String, Object> result = null;

        String login = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String commCareUrl = getCommcareProperty("commcare.authentication.url");

        if (isSuperUserEnabled() && isSuperUser(login, password)) {
            return createSuperUserAuthentication();
        }

        try {
            RestTemplate restTemplate = new RestTemplate(createSecureTransport(login, password));
            String userJson = restTemplate.getForObject(commCareUrl, String.class);
            result = new ObjectMapper().readValue(userJson, Map.class);
            UserEntity user = userService.login(login, (String) authentication.getCredentials());
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        } catch (EntityException e) {
            Map<String, String> user = ((ArrayList<Map<String, String>>) result.get("objects")).get(0);
            String userName = user.get("email");
            AreaEntity area = areaService.getAreaById(1);
            Set<RoleEntity> roles = userService.getAllRoles();

            userService.register(userName, password, area, roles);
            UserEntity userDashboard = userService.login(login,
                    (String) authentication.getCredentials());
            return new UsernamePasswordAuthenticationToken(userDashboard, null, userDashboard.getAuthorities());
        } catch (HttpClientErrorException | ArrayIndexOutOfBoundsException e) {
            throw new BadCredentialsException("Bad CommCare username / password!", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        userEntity.setArea(areaService.getAreaById(SUPER_USER_AREA_ID));
        userEntity.setUsername(SUPER_USER_NAME);
        return userEntity;
    }

    @SuppressWarnings("unchecked")
    protected ClientHttpRequestFactory createSecureTransport(String username, String password) {
        HttpClient client = new HttpClient();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        String commCareHost = getCommcareProperty("commcare.authentication.host");
        String commCarePort = getCommcareProperty("commcare.authentication.port");
        client.getState().setCredentials(new AuthScope(commCareHost, Integer.valueOf(commCarePort)), credentials);
        return new CommonsClientHttpRequestFactory(client);
    }

    private String getCommcareProperty(String propertyName) {
        return commCareConfiguration.getProperty(propertyName);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
