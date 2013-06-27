package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class IndicatorServiceImpl implements IndicatorService {

    @Autowired
    private IndicatorDao indicatorDao;

    @Override
    @Transactional
    public List<IndicatorEntity> findAllIndicators() {
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
        indicatorDao.update(indicatorEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicator(IndicatorEntity indicatorEntity) {
        indicatorDao.remove(indicatorEntity);
    }

}
