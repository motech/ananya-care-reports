package org.motechproject.carereporting.service;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LanguageEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class UserServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setupAuthentication() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("CAN_MANAGE_SYSTEM_USERS"));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("principal", "credentials", authorities));
    }

    @Test
    public void testRegisterUser() throws Exception {
        String username = "username3";
        UserEntity user = new UserEntity();
        user.setUsername(username);

        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(1);
        user.setDefaultLanguage(languageEntity);
        AreaEntity area = new AreaEntity();
        area.setId(1);
        user.setArea(area);
        userService.register(user);
        assertNotNull(userService.getUserByName(user.getUsername()));
    }

    @Test
    public void testAddRoles() throws Exception {
        addRoles("ROLE1", "ROLE2");
        assertEquals(6, userService.getAllRoles().size());
    }

    private void addRoles(String... roles) {
        for (String role: roles) {
            userService.addRole(role);
        }
    }

    @Test
    public void testRegisterUserWithRoles() throws Exception {
        String username = "username2";
        AreaEntity area = new AreaEntity();
        area.setId(1);
        userService.register(username, area, userService.getAllRoles());
        UserEntity user = userService.getUserByName(username);
        assertEquals(4, user.getRoles().size());
    }

    @Test
    public void testWrongUsername() throws Exception {
        String username = "bad-usernamepg";
        assertNull(userService.getUserByName(username));
    }

    @Test
    public void testUserCreationDate() {
        String username = "username4";
        AreaEntity area = new AreaEntity();
        area.setId(1);
        userService.register(username, area, new HashSet<RoleEntity>());
        UserEntity userEntity = userService.getUserByName(username);

        assertEquals(true, DateUtils.isSameDay(userEntity.getCreationDate(), new Date()));
    }

    @Test
    public void testUserModificationDate() {
        String username = "username";
        String newusername = "new user";
        UserEntity userEntity = new UserEntity(username);
        AreaEntity area = new AreaEntity();
        area.setId(1);
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(1);
        userEntity.setArea(area);
        userService.register(userEntity);
        userEntity.setUsername(newusername);
        userService.updateUser(userEntity);
        userEntity = userService.getUserByName(newusername);

        assertNotNull(userEntity.getModificationDate());
    }

}
