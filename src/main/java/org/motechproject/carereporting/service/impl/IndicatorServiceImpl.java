package org.motechproject.carereporting.service.impl;

import org.hibernate.exception.SQLGrammarException;
import org.motechproject.carereporting.dao.IndicatorCategoryDao;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.dao.IndicatorTypeDao;
import org.motechproject.carereporting.dao.IndicatorValueDao;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.exception.CareResourceNotFoundRuntimeException;
import org.motechproject.carereporting.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class IndicatorServiceImpl implements IndicatorService {

    private static final String ENTITY_DOES_NOT_EXIST_ERROR = "does not exist";

    @Autowired
    private IndicatorDao indicatorDao;

    @Autowired
    private IndicatorTypeDao indicatorTypeDao;

    @Autowired
    private IndicatorCategoryDao indicatorCategoryDao;

    @Autowired
    private IndicatorValueDao indicatorValueDao;

    // IndicatorEntity

    @Override
    @Transactional
    public Set<IndicatorEntity> findAllIndicators() {
        return indicatorDao.findAll();
    }

    @Override
    @Transactional
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
    public void updateIndicator(IndicatorEntity indicatorEntity) {
        try {
            indicatorDao.update(indicatorEntity);
        } catch (SQLGrammarException e) {
            if (e.getCause().getMessage().contains(ENTITY_DOES_NOT_EXIST_ERROR)) {
                throw new CareResourceNotFoundRuntimeException(IndicatorEntity.class, indicatorEntity.getId(), e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicator(IndicatorEntity indicatorEntity) {
        try {
            indicatorDao.remove(indicatorEntity);
        } catch (SQLGrammarException e) {
            if (e.getCause().getMessage().contains(ENTITY_DOES_NOT_EXIST_ERROR)) {
                throw new CareResourceNotFoundRuntimeException(IndicatorEntity.class, indicatorEntity.getId(), e);
            } else {
                throw new RuntimeException(e);
            }
        }
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
        try {
            indicatorTypeDao.update(indicatorTypeEntity);
        } catch (SQLGrammarException e) {
            if (e.getCause().getMessage().contains(ENTITY_DOES_NOT_EXIST_ERROR)) {
                throw new CareResourceNotFoundRuntimeException(IndicatorTypeEntity.class,
                        indicatorTypeEntity.getId(), e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicatorType(IndicatorTypeEntity indicatorTypeEntity) {
        try {
            indicatorTypeDao.remove(indicatorTypeEntity);
        } catch (SQLGrammarException e) {
            if (e.getCause().getMessage().contains(ENTITY_DOES_NOT_EXIST_ERROR)) {
                throw new CareResourceNotFoundRuntimeException(IndicatorTypeEntity.class,
                indicatorTypeEntity.getId(), e);
            } else {
                throw new RuntimeException(e);
            }
        }
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
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity) {
        try {
            indicatorCategoryDao.update(indicatorCategoryEntity);
        } catch (SQLGrammarException e) {
            if (e.getCause().getMessage().contains(ENTITY_DOES_NOT_EXIST_ERROR)) {
                throw new CareResourceNotFoundRuntimeException(IndicatorCategoryEntity.class,
                        indicatorCategoryEntity.getId(), e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity) {
        try {
            indicatorCategoryDao.remove(indicatorCategoryEntity);
        } catch (SQLGrammarException e) {
            if (e.getCause().getMessage().contains(ENTITY_DOES_NOT_EXIST_ERROR)) {
                throw new CareResourceNotFoundRuntimeException(IndicatorCategoryEntity.class,
                        indicatorCategoryEntity.getId(), e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional
    @Override
    public Set<IndicatorValueEntity> findAllIndicatorValues() {
        return indicatorValueDao.findAll();
    }

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
        try {
            indicatorValueDao.update(indicatorValueEntity);
        } catch (SQLGrammarException e) {
            if (e.getCause().getMessage().contains(ENTITY_DOES_NOT_EXIST_ERROR)) {
                throw new CareResourceNotFoundRuntimeException(IndicatorCategoryEntity.class,
                        indicatorValueEntity.getId(), e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicatorValue(IndicatorValueEntity indicatorValueEntity) {
        try {
            indicatorValueDao.remove(indicatorValueEntity);
        } catch (SQLGrammarException e) {
            if (e.getCause().getMessage().contains(ENTITY_DOES_NOT_EXIST_ERROR)) {
                throw new CareResourceNotFoundRuntimeException(IndicatorCategoryEntity.class,
                        indicatorValueEntity.getId(), e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }
}
