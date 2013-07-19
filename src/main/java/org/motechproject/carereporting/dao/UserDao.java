package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.UserEntity;

public interface UserDao extends GenericDao<UserEntity> {

    UserEntity getByUsernameAndPassword(String username, String password);

    String getSaltForUser(String username);
}
