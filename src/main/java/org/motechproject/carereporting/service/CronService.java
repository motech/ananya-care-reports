package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.CronTaskEntity;

import java.util.Set;

public interface CronService {

    Set<CronTaskEntity> getAllCronTasks();

    CronTaskEntity getCronTaskByName(String name);

    CronTaskEntity getDailyCronTask();

    void updateCronTask(CronTaskEntity cronTaskEntity);

}
