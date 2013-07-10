package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.ComparisonSymbolDao;
import org.motechproject.carereporting.dao.ComplexConditionDao;
import org.motechproject.carereporting.dao.ConditionDao;
import org.motechproject.carereporting.dao.OperatorTypeDao;
import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;
import org.motechproject.carereporting.domain.forms.ComplexConditionFormObject;
import org.motechproject.carereporting.service.ComplexConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ComplexConditionServiceImpl extends AbstractService implements ComplexConditionService {

    @Autowired
    private ComplexConditionDao complexConditionDao;

    @Autowired
    private ConditionDao conditionDao;

    @Autowired
    private OperatorTypeDao operatorTypeDao;

    @Autowired
    private ComparisonSymbolDao comparisonSymbolDao;

    @Override
    @Transactional
    public Set<ComplexConditionEntity> findAllComplexConditions() {
        return complexConditionDao.findAll();
    }

    @Override
    @Transactional
    public ComplexConditionEntity findComplexConditionById(Integer complexConditionId) {
        return complexConditionDao.findById(complexConditionId);
    }

    @Override
    @Transactional
    public Set<ComplexConditionEntity> findComplexConditionsByIndicatorId(Integer indicatorId) {
        return complexConditionDao.getAllByIndicatorId(indicatorId);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewComplexCondition(ComplexConditionEntity complexCondition) {
        complexConditionDao.save(complexCondition);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewComplexCondition(ComplexConditionFormObject complexConditionFormObject) {
        ComplexConditionEntity complexConditionEntity = new ComplexConditionEntity(
                complexConditionFormObject.getName(),
                findConditionsFromFormObject(complexConditionFormObject)
        );

        complexConditionDao.save(complexConditionEntity);
    }

    private Set<ConditionEntity> findConditionsFromFormObject(ComplexConditionFormObject complexConditionFormObject) {
        Set<ConditionEntity> conditions = new LinkedHashSet<>();

        for(Integer conditionId : complexConditionFormObject.getConditions()) {
            conditionDao.findById(conditionId);
        }

        return conditions;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateComplexCondition(ComplexConditionEntity complexConditionEntity) {
        complexConditionDao.update(complexConditionEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateComplexCondition(ComplexConditionFormObject complexConditionFormObject) {
        ComplexConditionEntity complexConditionEntity = this.findComplexConditionById(complexConditionFormObject.getId());

        complexConditionEntity.setName(complexConditionFormObject.getName());
        complexConditionEntity.setConditions(findConditionsFromFormObject(complexConditionFormObject));

        complexConditionDao.update(complexConditionEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteComplexCondition(ComplexConditionEntity complexConditionEntity) {
        complexConditionDao.remove(complexConditionEntity);
    }

    @Override
    @Transactional
    public Set<OperatorTypeEntity> findAllOperatorTypes() {
        return operatorTypeDao.findAll();
    }

    @Override
    @Transactional
    public OperatorTypeEntity findOperatorTypeById(Integer operatorTypeId) {
        return operatorTypeDao.findById(operatorTypeId);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewOperatorType(OperatorTypeEntity operatorType) {
        operatorTypeDao.save(operatorType);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateOperatorType(OperatorTypeEntity operatorTypeEntity) {
        operatorTypeDao.update(operatorTypeEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOperatorType(OperatorTypeEntity operatorTypeEntity) {
        operatorTypeDao.remove(operatorTypeEntity);
    }

    @Override
    @Transactional
    public Set<ComparisonSymbolEntity> findAllComparisonSymbols() {
        return comparisonSymbolDao.findAll();
    }

    @Override
    @Transactional
    public ComparisonSymbolEntity findComparisonSymbolById(Integer comparisonSymbolId) {
        return comparisonSymbolDao.findById(comparisonSymbolId);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewComparisonSymbol(ComparisonSymbolEntity comparisonSymbol) {
        comparisonSymbolDao.save(comparisonSymbol);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateComparisonSymbol(ComparisonSymbolEntity comparisonSymbolEntity) {
        comparisonSymbolDao.update(comparisonSymbolEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteComparisonSymbol(ComparisonSymbolEntity comparisonSymbolEntity) {
        comparisonSymbolDao.remove(comparisonSymbolEntity);
    }

}
