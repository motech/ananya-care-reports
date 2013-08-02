package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.LanguageEntity;

import java.util.Set;

public interface LanguageDao extends GenericDao<LanguageEntity> {

    Set<LanguageEntity> getAllWithDefined(Boolean isDefined);

    LanguageEntity getLanguageByCode(String languageCode);

}
