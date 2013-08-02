package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.CronTaskEntity;

public interface CronTaskDao extends GenericDao<CronTaskEntity> {

    CronTaskEntity getByName(String name);

}
