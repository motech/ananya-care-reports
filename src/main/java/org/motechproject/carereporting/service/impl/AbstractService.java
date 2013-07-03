package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.domain.AbstractEntity;
import org.motechproject.carereporting.exception.CareResourceNotFoundRuntimeException;

public abstract class AbstractService {

    protected void validateEntity(AbstractEntity entity) {
        if (entity == null) {
            throw new CareResourceNotFoundRuntimeException(entity.getClass(), entity.getId());
        }
    }
}
