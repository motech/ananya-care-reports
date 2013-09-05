package org.motechproject.carereporting.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LanguageEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.CareSqlRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class UserDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserDao userDao;

    @Test(expected = CareSqlRuntimeException.class)
    public void shouldThrowCareSqlRuntimeExceptionWhenEntityWithUniqueKeyAlreadyExistsDuringSave() {
        String username = "test";
        Integer id = 1;
        UserEntity userEntity = new UserEntity(username);

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
        UserEntity userEntity = userDao.getByField("username", username);
        userEntity.setUsername(newUsername);
        userDao.update(userEntity);
    }

    @Test
    public void doesUserExistWithCorrectUsernameShouldReturnTrue() {
        String username = "test";
        assertTrue(userDao.doesUserExist(username));
    }

    @Test
    public void doesUserExistWithCorrectUsernameShouldReturnFalse() {
        String username = "test12345";
        assertFalse(userDao.doesUserExist(username));
    }

}
