package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.FieldOperationEntity;

import java.util.Set;

public interface FieldOperationService {

    Set<FieldOperationEntity> getAllFieldOperations();

    FieldOperationEntity getFieldOperationEntityById(Integer fieldOperationEntityId);
}
