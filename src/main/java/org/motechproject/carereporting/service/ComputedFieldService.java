package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.forms.ComputedFieldFormObject;

import java.util.Set;

public interface ComputedFieldService {

    Set<ComputedFieldEntity> findAllComputedFields();

    ComputedFieldEntity findComputedFieldById(Integer computedFieldId);

    void createNewComputedField(ComputedFieldEntity computedFieldEntity);

    void createNewComputedFieldFromFormObject(ComputedFieldFormObject computedFieldFormObject);
}
