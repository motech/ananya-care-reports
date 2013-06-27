package org.motechproject.carereporting.init;

import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.service.UserException;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class TestDataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    private String testUsername = "test";
    private String testPassword = "test";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createAdminRole();
        createTestUser();
    }

    private void createAdminRole() {
        try {
            userService.addRole("ROLE_ADMIN");
        } catch (Exception e) {
            //role already exists - it's ok
        }
    }

    private void createTestUser() {
        try {
            userService.login(testUsername, testPassword);
        } catch (UserException e) {
            //cannot login to test user, let's create one
            userService.register(testUsername, testPassword, new HashSet<RoleEntity>(userService.getAllRoles()));
        }
    }

}
