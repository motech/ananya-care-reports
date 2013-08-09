package org.motechproject.carereporting.auth;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
        Map<String,Object> result = null;

        try {
            RestTemplate restTemplate = new RestTemplate(createSecureTransport((String) authentication.getPrincipal(),
                    (String) authentication.getCredentials()));

            String commCareUrl = commCareConfiguration.getProperty("commcare.authentication.url");

            String userJson = restTemplate.getForObject(commCareUrl, String.class);

            result = new ObjectMapper().readValue(userJson, Map.class);

            UserEntity user = userService.login((String) authentication.getPrincipal(),
                    (String) authentication.getCredentials());
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        } catch (EntityException e) {
            Map<String, String> user = ((ArrayList<Map<String, String>>)result.get("objects")).get(0);
            String userName = user.get("email");
            String password = (String) authentication.getCredentials();
            String firstName = user.get("first_name");
            String lastName = user.get("last_name");
            AreaEntity area = areaService.getAreaById(1);
            Set<RoleEntity> roles = userService.getAllRoles();

            userService.register(userName, password, firstName, lastName, area, roles);
            UserEntity userDashboard = userService.login((String) authentication.getPrincipal(),
                    (String) authentication.getCredentials());
            return new UsernamePasswordAuthenticationToken(userDashboard, null, userDashboard.getAuthorities());
        } catch (HttpClientErrorException e) {
            throw new BadCredentialsException("Bad CommCare username / password!", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected ClientHttpRequestFactory createSecureTransport(String username, String password){
        HttpClient client = new HttpClient();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username,password);
        String commCareHost = commCareConfiguration.getProperty("commcare.authentication.host");
        String commCarePort = commCareConfiguration.getProperty("commcare.authentication.port");
        client.getState().setCredentials(new AuthScope(commCareHost, Integer.valueOf(commCarePort)), credentials);
        return new CommonsClientHttpRequestFactory(client);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
