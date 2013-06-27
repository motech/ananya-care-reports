package org.motechproject.carereporting.service;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void deleteUsersAndRoles() {
        sessionFactory.getCurrentSession()
                .createQuery("delete from UserEntity");
        sessionFactory.getCurrentSession()
                .createQuery("delete from RoleEntity");
    }

    @Test
    public void testRegisterUser() throws Exception {
        String username = "username";
        String password = "password";
        userService.register(username, password, new HashSet<RoleEntity>());
        UserEntity user = userService.login(username, password);
        assertNotNull(user);
    }

    @Test
    public void testAddRoles() throws Exception {
        addRoles("ROLE1", "ROLE2");
        assertEquals(3, userService.getAllRoles().size()); //we expect 3, since ROLE_ADMIN is added on startup
    }

    @Test
    public void testRegisterUserWithRoles() throws Exception {
        addRoles("ROLE1", "ROLE2", "ROLE3");
        RoleEntity role = userService.getAllRoles().get(0);
        String username = "username";
        String password = "password";
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        userService.register(username, password, roles);
        UserEntity user = userService.login(username, password);
        assertEquals(1, user.getRoles().size());
    }

    private void addRoles(String... roles) {
        for (String role: roles) {
            userService.addRole(role);
        }
    }

    @Test(expected = UserException.class)
    public void testLoginFailed() throws Exception {
        String username = "bad-username";
        String password = "bad-password";
        userService.login(username, password);
    }

}
