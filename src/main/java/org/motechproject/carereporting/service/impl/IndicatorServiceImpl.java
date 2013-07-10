package org.motechproject.carereporting.service.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.motechproject.carereporting.dao.IndicatorCategoryDao;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.dao.IndicatorTypeDao;
import org.motechproject.carereporting.dao.IndicatorValueDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.forms.IndicatorFormObject;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.ComplexConditionService;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.motechproject.carereporting.service.DashboardService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class IndicatorServiceImpl extends AbstractService implements IndicatorService {

    @Autowired
    private IndicatorDao indicatorDao;

    @Autowired
    private IndicatorTypeDao indicatorTypeDao;

    @Autowired
    private IndicatorCategoryDao indicatorCategoryDao;

    @Autowired
    private IndicatorValueDao indicatorValueDao;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ComplexConditionService complexConditionService;

    @Autowired
    private ComputedFieldService computedFieldService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionFactory sessionFactory;

    public void setIndicatorDao(IndicatorDao indicatorDao) {
        this.indicatorDao = indicatorDao;
    }

    public void setIndicatorTypeDao(IndicatorTypeDao indicatorTypeDao) {
        this.indicatorTypeDao = indicatorTypeDao;
    }

    public void setIndicatorCategoryDao(IndicatorCategoryDao indicatorCategoryDao) {
        this.indicatorCategoryDao = indicatorCategoryDao;
    }

    public void setIndicatorValueDao(IndicatorValueDao indicatorValueDao) {
        this.indicatorValueDao = indicatorValueDao;
    }
// IndicatorEntity

    @Transactional
    public Set<IndicatorEntity> findAllIndicators() {
        return indicatorDao.findAll();
    }

    private Set<AreaEntity> getAllChildEntities(AreaEntity areaEntity) {
        Set<AreaEntity> areas = new LinkedHashSet<>();

        areas.add(areaEntity);
        for (AreaEntity area : areaEntity.getChildAreas()) {
            areas.addAll(getAllChildEntities(area));
        }

        return areas;
    }

    @Transactional
    @Override
    public Set<IndicatorEntity> findAllIndicatorsUnderUserArea(Integer areaId) {
        AreaEntity areaEntity = areaService.findAreaById(areaId);
        Set<AreaEntity> areaEntities = getAllChildEntities(areaEntity);

        List<Integer> areaIds = new ArrayList<>();
        for (AreaEntity area : areaEntities) {
            areaIds.add(area.getId());
        }

        Query query = sessionFactory.getCurrentSession()
                .createQuery("from IndicatorEntity where area.id in (:areaEntities)");
        query.setParameterList("areaEntities", areaIds);

        return new LinkedHashSet<IndicatorEntity>(query.list());
    }

    @Transactional
    @Override
    public IndicatorEntity findIndicatorById(Integer id) {
        return indicatorDao.findById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewIndicator(IndicatorEntity indicatorEntity) {
        indicatorDao.save(indicatorEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewIndicatorFromFormObject(IndicatorFormObject indicatorFormObject) {

        IndicatorEntity indicatorEntity = new IndicatorEntity(
                findIndicatorTypeEntityFromFormObject(indicatorFormObject),
                findIndicatorCategoryEntitiesFromFormObject(indicatorFormObject),
                findAreaEntityFromFormObject(indicatorFormObject),
                findUserEntitiesFromFormObject(indicatorFormObject),
                findComputedFieldEntityFromFormObject(indicatorFormObject),
                findComplexConditionEntityFromFormObject(indicatorFormObject),
                findIndicatorValueEntitiesFromFormObject(indicatorFormObject),
                indicatorFormObject.getFrequency(),
                indicatorFormObject.getName());

        indicatorDao.save(indicatorEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicator(IndicatorEntity indicatorEntity) {
        indicatorDao.update(indicatorEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicatorFromFormObject(IndicatorFormObject indicatorFormObject) {
        IndicatorEntity indicatorEntity = this.findIndicatorById(indicatorFormObject.getId());

        indicatorEntity.setIndicatorType(findIndicatorTypeEntityFromFormObject(indicatorFormObject));
        indicatorEntity.setCategories(findIndicatorCategoryEntitiesFromFormObject(indicatorFormObject));
        indicatorEntity.setArea(findAreaEntityFromFormObject(indicatorFormObject));
        indicatorEntity.setOwners(findUserEntitiesFromFormObject(indicatorFormObject));
        indicatorEntity.setComputedField(findComputedFieldEntityFromFormObject(indicatorFormObject));
        indicatorEntity.setComplexCondition(findComplexConditionEntityFromFormObject(indicatorFormObject));
        indicatorEntity.setValues(findIndicatorValueEntitiesFromFormObject(indicatorFormObject));
        indicatorEntity.setFrequency(indicatorFormObject.getFrequency());
        indicatorEntity.setName(indicatorFormObject.getName());

        indicatorDao.update(indicatorEntity);
    }

    private IndicatorTypeEntity findIndicatorTypeEntityFromFormObject(IndicatorFormObject indicatorFormObject) {
        return findIndicatorTypeById(indicatorFormObject.getIndicatorType());
    }

    private Set<IndicatorCategoryEntity> findIndicatorCategoryEntitiesFromFormObject(
            IndicatorFormObject indicatorFormObject) {
        Set<IndicatorCategoryEntity> indicatorCategoryEntities = new LinkedHashSet<>();

        for (Integer indicatorCategoryId : indicatorFormObject.getCategories()) {
            IndicatorCategoryEntity indicatorCategoryEntity = this.findIndicatorCategoryById(indicatorCategoryId);

            indicatorCategoryEntities.add(indicatorCategoryEntity);
        }

        return indicatorCategoryEntities;
    }

    private AreaEntity findAreaEntityFromFormObject(IndicatorFormObject indicatorFormObject) {
        return areaService.findAreaById(indicatorFormObject.getArea());
    }

    private Set<UserEntity> findUserEntitiesFromFormObject(IndicatorFormObject indicatorFormObject) {
        Set<UserEntity> userEntities = new LinkedHashSet<>();

        for (Integer ownerId : indicatorFormObject.getOwners()) {
            UserEntity userEntity = userService.findUserById(ownerId);

            userEntities.add(userEntity);
        }

        return userEntities;
    }

    private ComputedFieldEntity findComputedFieldEntityFromFormObject(
            IndicatorFormObject indicatorFormObject) {
        return computedFieldService.findComputedFieldById(indicatorFormObject.getComputedField());
    }

    private ComplexConditionEntity findComplexConditionEntityFromFormObject(
            IndicatorFormObject indicatorFormObject) {
        return complexConditionService.findComplexConditionById(indicatorFormObject.getComplexCondition());
    }

    private Set<IndicatorValueEntity> findIndicatorValueEntitiesFromFormObject(
            IndicatorFormObject indicatorFormObject) {
        Set<IndicatorValueEntity> indicatorValueEntities = new LinkedHashSet<>();

        for (Integer valueId : indicatorFormObject.getValues()) {
            IndicatorValueEntity indicatorValueEntity = this.findIndicatorValueById(valueId);

            indicatorValueEntities.add(indicatorValueEntity);
        }

        return indicatorValueEntities;
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicator(IndicatorEntity indicatorEntity) {
        indicatorDao.remove(indicatorEntity);
    }

    // IndicatorTypeEntity

    @Transactional
    @Override
    public Set<IndicatorTypeEntity> findAllIndicatorTypes() {
        return indicatorTypeDao.findAll();
    }

    @Transactional
    @Override
    public IndicatorTypeEntity findIndicatorTypeById(Integer id) {
        return indicatorTypeDao.findById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewIndicatorType(IndicatorTypeEntity indicatorTypeEntity) {
        indicatorTypeDao.save(indicatorTypeEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicatorType(IndicatorTypeEntity indicatorTypeEntity) {
        indicatorTypeDao.update(indicatorTypeEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicatorType(IndicatorTypeEntity indicatorTypeEntity) {
        indicatorTypeDao.remove(indicatorTypeEntity);
    }

    // IndicatorCategoryEntity

    @Transactional
    @Override
    public Set<IndicatorCategoryEntity> findAllIndicatorCategories() {
        return indicatorCategoryDao.findAll();
    }

    @Transactional
    @Override
    public IndicatorCategoryEntity findIndicatorCategoryById(Integer id) {
        return indicatorCategoryDao.findById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity) {
        indicatorCategoryDao.save(indicatorCategoryEntity);
        createDashboardForNewIndicatorCategory(indicatorCategoryEntity.getName());
    }

    private void createDashboardForNewIndicatorCategory(String name) {
        Short newDashboardTabPosition = dashboardService.getTabPositionForNewDashboard();
        Set<UserEntity> dashboardOwners = new HashSet<UserEntity>();
        dashboardOwners.add(userService.findCurrentlyLoggedUser());
        DashboardEntity dashboardEntity = new DashboardEntity(name, newDashboardTabPosition, dashboardOwners);
        dashboardService.createNewDashboard(dashboardEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity) {
        indicatorCategoryDao.update(indicatorCategoryEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity) {
        indicatorCategoryDao.remove(indicatorCategoryEntity);
    }

    @Transactional
    @Override
    public Set<IndicatorValueEntity> findAllIndicatorValues() {
        return indicatorValueDao.findAll();
    }

    // IndicatorValueEntity

    @Transactional
    @Override
    public IndicatorValueEntity findIndicatorValueById(Integer id) {
        return indicatorValueDao.findById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewIndicatorValue(IndicatorValueEntity indicatorValueEntity) {
        indicatorValueDao.save(indicatorValueEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicatorValue(IndicatorValueEntity indicatorValueEntity) {
        indicatorValueDao.update(indicatorValueEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicatorValue(IndicatorValueEntity indicatorValueEntity) {
        indicatorValueDao.remove(indicatorValueEntity);
    }
}
