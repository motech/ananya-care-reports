package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.ComplexConditionDao;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.springframework.stereotype.Component;

@Component
public class ComplexConditionDaoHibernateImpl
        extends GenericDaoHibernateImpl<ComplexConditionEntity> implements ComplexConditionDao {
}
