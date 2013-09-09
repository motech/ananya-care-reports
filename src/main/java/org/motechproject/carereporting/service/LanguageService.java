package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.LanguageEntity;
import org.motechproject.carereporting.domain.dto.MessageDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface LanguageService {

    String HAS_ROLE_CAN_CREATE_LANGUAGES = "hasRole('CAN_CREATE_LANGUAGES')";
    String HAS_ROLE_CAN_EDIT_LANGUAGES = "hasRole('CAN_EDIT_LANGUAGES')";
    String HAS_ROLE_CAN_REMOVE_LANGUAGES = "hasRole('CAN_REMOVE_LANGUAGES')";

    Set<LanguageEntity> getAllLanguages();

    LanguageEntity getLanguageByCode(String languageCode);

    Set<MessageDto> getMessagesForLanguage(String languageCode);

    String getMessagesForLanguagePlain(String languageCode);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_LANGUAGES)
    void defineLanguage(LanguageEntity languageEntity);

    @PreAuthorize(HAS_ROLE_CAN_EDIT_LANGUAGES)
    void updateLanguage(LanguageEntity languageEntity);

    @PreAuthorize(HAS_ROLE_CAN_REMOVE_LANGUAGES)
    void removeLanguage(String languageCode);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_LANGUAGES)
    void setMessagesForLanguage(LanguageEntity languageEntity);

}
