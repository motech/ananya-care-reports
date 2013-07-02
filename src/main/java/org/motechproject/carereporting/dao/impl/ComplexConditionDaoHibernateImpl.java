package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.ComplexConditionDao;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplexConditionDaoHibernateImpl
        extends GenericDaoHibernateImpl<ComplexConditionEntity> implements ComplexConditionDao {

    // TODO: Implement finding all complex conditions by indicator id
    @Override
    public List<ComplexConditionEntity> getAllByIndicatorId(Integer indicatorId) {
        throw new UnsupportedOperationException();
    }
}
