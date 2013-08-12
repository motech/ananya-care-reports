package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.ComparisonSymbolDao;
import org.motechproject.carereporting.dao.ComplexConditionDao;
import org.motechproject.carereporting.dao.OperatorTypeDao;
import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;
import org.motechproject.carereporting.domain.dto.ComplexConditionDto;
import org.motechproject.carereporting.service.ComplexConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ComplexConditionServiceImpl implements ComplexConditionService {

    @Autowired
    private ComplexConditionDao complexConditionDao;

    @Autowired
    private OperatorTypeDao operatorTypeDao;

    @Autowired
    private ComparisonSymbolDao comparisonSymbolDao;

    @Override
    @Transactional
    public Set<ComplexConditionEntity> getAllComplexConditions() {
        return complexConditionDao.getAll();
    }

    @Override
    @Transactional
    public ComplexConditionEntity getComplexConditionById(Integer complexConditionId) {
        return complexConditionDao.getById(complexConditionId);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewComplexCondition(ComplexConditionEntity complexCondition) {
        complexConditionDao.save(complexCondition);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewComplexCondition(ComplexConditionDto complexConditionDto) {
        ComplexConditionEntity complexConditionEntity = new ComplexConditionEntity(
                complexConditionDto.getName()
        );

        complexConditionDao.save(complexConditionEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateComplexCondition(ComplexConditionEntity complexConditionEntity) {
        complexConditionDao.update(complexConditionEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateComplexCondition(ComplexConditionDto complexConditionDto) {
        ComplexConditionEntity complexConditionEntity = this.getComplexConditionById(complexConditionDto.getId());

        complexConditionEntity.setName(complexConditionDto.getName());

        complexConditionDao.update(complexConditionEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteComplexCondition(ComplexConditionEntity complexConditionEntity) {
        complexConditionDao.remove(complexConditionEntity);
    }

    @Override
    @Transactional
    public Set<OperatorTypeEntity> getAllOperatorTypes() {
        return operatorTypeDao.getAll();
    }

    @Override
    @Transactional
    public OperatorTypeEntity getOperatorTypeById(Integer operatorTypeId) {
        return operatorTypeDao.getById(operatorTypeId);
    }

    @Override
    @Transactional
    public Set<ComparisonSymbolEntity> getAllComparisonSymbols() {
        return comparisonSymbolDao.getAll();
    }

    @Override
    @Transactional
    public ComparisonSymbolEntity getComparisonSymbolById(Integer comparisonSymbolId) {
        return comparisonSymbolDao.getById(comparisonSymbolId);
    }

}
