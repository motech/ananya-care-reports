package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.Set;

public interface CronService {

    String HAS_ROLE_CAN_EDIT_CALCULATION = "hasRole('CAN_EDIT_CALCULATION')";

    Set<CronTaskEntity> getAllCronTasks();

    CronTaskEntity getCronTaskByFrequencyName(String name);

    CronTaskEntity getDailyCronTask();

    @PreAuthorize(HAS_ROLE_CAN_EDIT_CALCULATION)
    void updateCronTask(CronTaskEntity cronTaskEntity);

    FrequencyEntity getFrequencyByName(String name);

    Set<FrequencyEntity> getAllFrequencies();

    FrequencyEntity getFrequencyById(Integer frequencyId);

    Date getDateDepth();

    @PreAuthorize(HAS_ROLE_CAN_EDIT_CALCULATION)
    void updateDateDepth(Date newDateDepth);

}
