package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.UserEntity;

public interface UserDao extends GenericDao<UserEntity> {

    boolean doesUserExist(String userName);
}
