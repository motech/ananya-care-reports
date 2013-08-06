package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;

import java.util.Set;

public interface CronService {

    Set<CronTaskEntity> getAllCronTasks();

    CronTaskEntity getCronTaskByFrequencyName(String name);

    CronTaskEntity getDailyCronTask();

    void updateCronTask(CronTaskEntity cronTaskEntity);

    FrequencyEntity getFrequencyByName(String name);

}
