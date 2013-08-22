package org.motechproject.carereporting.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LanguageEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.CareSqlRuntimeException;
import org.motechproject.carereporting.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class UserDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void testGetByUsernameAndPassword() {
        String name = "test";
        String password = "51abb9636078defbf888d8457a7c76f85c8f114c";
        UserEntity user = userDao.getByUsernameAndPassword(name, password);
        Assert.assertNotNull(user);
    }

    @Test(expected = EntityException.class)
    public void testGetByUsernameAndPasswordFailed() {
        String name = "bad name";
        String password = "bad password";
        userDao.getByUsernameAndPassword(name, password);
    }

    @Test
    public void testGetSalt() {
        String name = "test";
        Assert.assertEquals("test", userDao.getSaltForUser(name));
    }

    @Test(expected = EntityException.class)
    public void testGetForNonExistingUser() {
        String name = "bad name";
        userDao.getSaltForUser(name);
    }

    @Test(expected = CareSqlRuntimeException.class)
    public void shouldThrowCareSqlRuntimeExceptionWhenEntityWithUniqueKeyAlreadyExistsDuringSave() {
        String username = "test";
        String password = "pass";
        Integer id = 1;
        UserEntity userEntity = new UserEntity(username, password);

        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(1);
        userEntity.setDefaultLanguage(languageEntity);
        LevelEntity levelEntity = new LevelEntity(username, null);
        levelEntity.setId(id);
        AreaEntity areaEntity = new AreaEntity(username, levelEntity);
        areaEntity.setId(id);
        userEntity.setArea(areaEntity);

        userDao.save(userEntity);
    }

    @Test(expected = CareSqlRuntimeException.class)
    public void shouldThrowCareSqlRuntimeExceptionWhenEntityWithUniqueKeyAlreadyExistsDuringUpdate() {
        String username = "test";
        String newUsername = "soldeveloper";
        String password = "51abb9636078defbf888d8457a7c76f85c8f114c";
        UserEntity userEntity = userDao.getByUsernameAndPassword(username, password);
        userEntity.setUsername(newUsername);

        userDao.update(userEntity);
    }

}
