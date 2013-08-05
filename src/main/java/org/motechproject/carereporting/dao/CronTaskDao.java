package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;

public interface CronTaskDao extends GenericDao<CronTaskEntity> {

    CronTaskEntity getByFrequency(FrequencyEntity frequencyEntity);

}
