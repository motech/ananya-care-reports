package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.enums.FieldType;

import java.util.Set;

public interface FieldService {

    Set<FieldEntity> findAllFieldsByFormId(Integer formId);

    Set<FieldEntity> findAllFieldsByType(FieldType fieldType);

    FieldEntity findFieldById(Integer fieldId);

    void createNewField(FieldEntity fieldEntity);
}
