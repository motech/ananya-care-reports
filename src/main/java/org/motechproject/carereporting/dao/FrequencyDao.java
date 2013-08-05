package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.FrequencyEntity;

public interface FrequencyDao extends GenericDao<FrequencyEntity> {

    FrequencyEntity getByFrequencyName(String name);

}
