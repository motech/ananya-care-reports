package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ComputedFieldEntity;

import java.util.Set;

public interface ComputedFieldService {

    Set<ComputedFieldEntity> findAllComputedFields();

    ComputedFieldEntity findComputedFieldById(Integer computedFieldId);

    void createNewComputedField(ComputedFieldEntity computedFieldEntity);
}
