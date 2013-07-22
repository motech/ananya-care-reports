package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.FieldOperationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class FieldOperationServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private FieldOperationService fieldOperationService;

    private final static int EXPECTED_FIELD_OPERATIONS_ALL = 817;
    private final static Integer FIELD_OPERATION_ID = 1;

    @Test
    public void testGetAllFieldOperations() {
        Set<FieldOperationEntity> fieldOperationEntities = fieldOperationService.getAllFieldOperations();

        assertNotNull(fieldOperationEntities);
        assertEquals(EXPECTED_FIELD_OPERATIONS_ALL, fieldOperationEntities.size());
    }

    @Test
    public void testGetFieldOperationEntityById() {
        FieldOperationEntity fieldOperationEntity = fieldOperationService
                .getFieldOperationEntityById(FIELD_OPERATION_ID);

        assertNotNull(fieldOperationEntity);
        assertEquals(FIELD_OPERATION_ID, fieldOperationEntity.getId());
    }
}
