package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.FieldOperationEntity;

import java.util.Set;

public interface FieldOperationService {

    Set<FieldOperationEntity> findAllFieldOperationServices();

    FieldOperationEntity findFieldOperationEntityById(Integer fieldOperationEntityId);
}
