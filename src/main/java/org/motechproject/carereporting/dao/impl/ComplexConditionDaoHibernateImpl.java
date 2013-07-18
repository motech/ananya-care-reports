package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.ComplexConditionDao;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ComplexConditionDaoHibernateImpl
        extends GenericDaoHibernateImpl<ComplexConditionEntity> implements ComplexConditionDao {

    @Override
    public Set<ComplexConditionEntity> getAllByIndicatorId(Integer indicatorId) {
        throw new UnsupportedOperationException();
    }
}
