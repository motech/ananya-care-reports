package org.motechproject.carereporting.service;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.enums.FieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class FieldServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private FormsService formsService;

    @Autowired
    private SessionFactory sessionFactory;

    private final static Integer FORM_ID = 1;
    private final static int EXPECTED_FIELD_NAMES_BY_FORM_ID = 7;
    private final static int EXPECTED_FIELDS_BY_FORM_ID = 7;
    private final static FieldType FIELD_TYPE = FieldType.String;
    private final static int EXPECTED_FIELDS_BY_TYPE = 131;
    private final static Integer FIELD_ID = 1;
    private final static String FIELD_NAME = "FIELD_TEST_1";

    private static Integer newFieldId;

    @After
    public void cleanup() {
        deleteCreatedField();
    }

    private void deleteCreatedField() {
        if (newFieldId == null) {
            return;
        }

        Query query = sessionFactory.getCurrentSession()
                .createQuery("delete FieldEntity where id = :fieldId");
        query.setParameter("fieldId", newFieldId);

        newFieldId = null;
    }

    @Test
    public void testGetAllFieldNamesByFormId() {
        List<String> fieldNames = fieldService.getAllFieldNamesByFormId(FORM_ID);

        assertNotNull(fieldNames);
        assertEquals(EXPECTED_FIELD_NAMES_BY_FORM_ID, fieldNames.size());
    }

    @Test
    public void testGetAllFieldsByFormId() {
        Set<FieldEntity> fieldEntities = fieldService.getAllFieldsByFormId(FORM_ID);

        assertNotNull(fieldEntities);
        assertEquals(EXPECTED_FIELDS_BY_FORM_ID, fieldEntities.size());
    }

    @Test
    public void testGetAllFieldByType() {
        Set<FieldEntity> fieldEntities = fieldService.getAllFieldsByType(FIELD_TYPE);

        assertNotNull(fieldEntities);
        assertEquals(EXPECTED_FIELDS_BY_TYPE, fieldEntities.size());
    }

    @Test
    public void testGetFieldById() {
        FieldEntity fieldEntity = fieldService.getFieldById(FIELD_ID);

        assertNotNull(fieldEntity);
        assertEquals(FIELD_ID, fieldEntity.getId());
    }

    @Test
    public void testCreateField() {
        createField();
    }

    private FieldEntity createField() {
        FormEntity formEntity = formsService.getFormById(FORM_ID);

        assertNotNull(formEntity);
        assertEquals(FORM_ID, formEntity.getId());

        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setName(FIELD_NAME);
        fieldEntity.setType(FIELD_TYPE);
        fieldEntity.setForm(formEntity);

        fieldService.createNewField(fieldEntity);
        newFieldId = fieldEntity.getId();

        fieldEntity = fieldService.getFieldById(newFieldId);

        assertNotNull(fieldEntity);
        assertEquals(newFieldId, fieldEntity.getId());
        assertEquals(FIELD_NAME, fieldEntity.getName());
        assertEquals(FIELD_TYPE, fieldEntity.getType());

        return fieldEntity;
    }
}
