package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.IndicatorEntity;

import java.util.List;

public interface IndicatorService {

    List<IndicatorEntity> findAllIndicators();
    IndicatorEntity findIndicatorById(Integer id);

    void createNewIndicator(IndicatorEntity indicatorEntity);
    void updateIndicator(IndicatorEntity indicatorEntity);
    void deleteIndicator(IndicatorEntity indicatorEntity);
}
