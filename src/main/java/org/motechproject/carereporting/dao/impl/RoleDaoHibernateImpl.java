package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.RoleDao;
import org.motechproject.carereporting.domain.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleDaoHibernateImpl extends GenericDaoHibernateImpl<RoleEntity> implements RoleDao {

    public RoleEntity getByName(String name) {
        return getByField("name", name);
    }
}
