package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.OperatorTypeDao;
import org.motechproject.carereporting.domain.OperatorTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class OperatorTypeDaoHibernateImpl
        extends GenericDaoHibernateImpl<OperatorTypeEntity> implements OperatorTypeDao {
}
