package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.UserEntity;

public interface UserDao extends GenericDao<UserEntity> {

    UserEntity findByUsernameAndPassword(String username, String password);

    String getSaltForUser(String username);
}
