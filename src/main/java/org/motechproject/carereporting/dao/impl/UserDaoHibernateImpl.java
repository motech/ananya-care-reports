package org.motechproject.carereporting.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.UserDao;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.EntityException;
import org.springframework.stereotype.Component;

@Component
public class UserDaoHibernateImpl extends GenericDaoHibernateImpl<UserEntity> implements UserDao {

    @Override
    public UserEntity findByUsernameAndPassword(String username, String password) {
        UserEntity user = (UserEntity) getSessionFactory().getCurrentSession().createCriteria(UserEntity.class)
            .add(Restrictions.eq("username", username))
            .add(Restrictions.eq("password", password))
            .uniqueResult();

        if(user == null) {
            throw new EntityException("Bad username or password");
        }

        return user;
    }

    @Override
    public String getSaltForUser(String username) {
        UserEntity user = (UserEntity) getSessionFactory().getCurrentSession().createCriteria(UserEntity.class)
                .add(Restrictions.eq("username", username))
                .uniqueResult();

        if (user == null) {
            throw new EntityException("Bad username or password");
        }

        return user.getSalt();
    }
}
