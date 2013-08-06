package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.LanguageEntity;
import org.motechproject.carereporting.domain.dto.MessageDto;

import java.util.Set;

public interface LanguageService {

    Set<LanguageEntity> getAllLanguages();

    LanguageEntity getLanguageByCode(String languageCode);

    Set<MessageDto> getMessagesForLanguage(String languageCode);

    String getMessagesForLanguagePlain(String languageCode);

    void defineLanguage(LanguageEntity languageEntity);

    void updateLanguage(LanguageEntity languageEntity);

    void removeLanguage(String languageCode);

    void setMessagesForLanguage(LanguageEntity languageEntity);

}
