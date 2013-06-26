package org.motechproject.carereporting.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.UserDao;
import org.motechproject.carereporting.domain.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDaoHibernateImpl extends GenericDaoHibernateImpl<UserEntity> implements UserDao {

    @Override
    public UserEntity findByUsernameAndPassword(String username, String password) {
        return (UserEntity) getSessionFactory().getCurrentSession().createCriteria(UserEntity.class)
            .add(Restrictions.eq("username", username))
            .add(Restrictions.eq("password", password))
            .uniqueResult();
    }
}
