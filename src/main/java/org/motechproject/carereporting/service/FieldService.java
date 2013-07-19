package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.enums.FieldType;

import java.util.List;
import java.util.Set;

public interface FieldService {

    List<String> getAllFieldNamesByFormId(Integer formId);

    Set<FieldEntity> getAllFieldsByFormId(Integer formId);

    Set<FieldEntity> getAllFieldsByType(FieldType fieldType);

    FieldEntity getFieldById(Integer fieldId);

    void createNewField(FieldEntity fieldEntity);
}
