package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.UserDao;
import org.motechproject.carereporting.domain.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDaoHibernateImpl extends GenericDaoHibernateImpl<UserEntity> implements UserDao {

    @Override
    public boolean doesUserExist(String userName) {
        return !getSessionFactory()
                .getCurrentSession()
                .createQuery("select count(*) from UserEntity where username = :userName")
                .setParameter("userName", userName)
                .uniqueResult().equals(Long.valueOf(0));
    }

}
