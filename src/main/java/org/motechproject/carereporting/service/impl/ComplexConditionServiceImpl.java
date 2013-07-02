package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.ComparisonSymbolDao;
import org.motechproject.carereporting.dao.ComplexConditionDao;
import org.motechproject.carereporting.dao.OperatorTypeDao;
import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;
import org.motechproject.carereporting.service.ComplexConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<ComplexConditionEntity> getAllComplexConditions() {
        return complexConditionDao.findAll();
    }

    @Override
    @Transactional
    public ComplexConditionEntity getComplexConditionById(Integer complexConditionId) {
        return complexConditionDao.findById(complexConditionId);
    }

    @Override
    @Transactional
    public List<ComplexConditionEntity> getComplexConditionsByIndicatorId(Integer indicatorId) {
        return complexConditionDao.getAllByIndicatorId(indicatorId);
    }

    @Override
    @Transactional(readOnly = false)
    public void createComplexCondition(ComplexConditionEntity complexCondition) {
        complexConditionDao.save(complexCondition);
    }

    @Override
    @Transactional
    public List<OperatorTypeEntity> getAllOperatorTypes() {
        return operatorTypeDao.findAll();
    }

    @Override
    @Transactional
    public OperatorTypeEntity getOperatorTypeById(Integer operatorTypeId) {
        return operatorTypeDao.findById(operatorTypeId);
    }

    @Override
    @Transactional(readOnly = false)
    public void createOperatorType(OperatorTypeEntity operatorType) {
        operatorTypeDao.save(operatorType);
    }

    @Override
    @Transactional
    public List<ComparisonSymbolEntity> getAllComparisonSymbols() {
        return comparisonSymbolDao.findAll();
    }

    @Override
    @Transactional
    public ComparisonSymbolEntity getComparisonSymbolById(Integer comparisonSymbolId) {
        return comparisonSymbolDao.findById(comparisonSymbolId);
    }

    @Override
    @Transactional(readOnly = false)
    public void createComparisonSymbol(ComparisonSymbolEntity comparisonSymbol) {
        comparisonSymbolDao.save(comparisonSymbol);
    }
}
