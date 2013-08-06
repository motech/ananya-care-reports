package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.LanguageEntity;

public interface LanguageDao extends GenericDao<LanguageEntity> {

    LanguageEntity getLanguageByCode(String languageCode);

    void deleteByCode(String languageCode);

}
