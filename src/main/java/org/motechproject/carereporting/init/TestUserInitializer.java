package org.motechproject.carereporting.init;

import org.motechproject.carereporting.service.UserException;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TestUserInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    private String testUsername = "test";
    private String testPassword = "test";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            userService.login(testUsername, testPassword);
        } catch (UserException e) {
            //cannot login to test user, let's create one
            ArrayList<String> roles = new ArrayList<>();
            roles.add("ROLE_TEST");
            userService.register(testUsername, testPassword, roles);
        }
    }

}
