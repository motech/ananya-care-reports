package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.CronTaskEntity;

public interface CronService {

    CronTaskEntity getDefaultCronTask();

    CronTaskEntity getCronTaskByName(String name);

    void updateCronTask(CronTaskEntity cronTaskEntity);

}
