package org.motechproject.carereporting.service;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.domain.FieldOperationEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.dto.ComputedFieldFormObject;
import org.motechproject.carereporting.enums.FieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class ComputedFieldServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ComputedFieldService computedFieldService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private FormsService formsService;

    @Autowired
    private SessionFactory sessionFactory;

    private static final int EXPECTED_COMPUTED_FIELDS_ALL = 818;
    private static final Integer FORM_ID = 1;
    private static final int EXPECTED_COMPUTED_FIELDS_BY_FORM_ID = 8;
    private static final Integer FIELD_ID = 1;
    private static final Integer COMPUTED_FIELD_ID = 1;
    private static final String COMPUTED_FIELD_NAME = "COMPUTED_FIELD_TEST_1";
    private static final FieldType COMPUTED_FIELD_TYPE = FieldType.Number;

    private static Integer newComputedFieldId;

    @Before
    public void setupAuthentication() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("CAN_CREATE_COMPUTED_FIELDS"));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("principal", "credentials", authorities));
    }

    @After
    public void cleanup() {
        deleteCreatedComputedField();
    }

    private void deleteCreatedComputedField() {
        if (newComputedFieldId == null) {
            return;
        }

        Query query = sessionFactory.getCurrentSession()
                .createQuery("delete ComputedFieldEntity where id = :computedFieldId");
        query.setParameter("computedFieldId", newComputedFieldId);

        newComputedFieldId = null;
    }

    @Test
    public void testgetAllComputedFields() {
        Set<ComputedFieldEntity> computedFieldEntities = computedFieldService.getAllComputedFields();

        assertNotNull(computedFieldEntities);
        assertEquals(EXPECTED_COMPUTED_FIELDS_ALL, computedFieldEntities.size());
    }

    @Test
    public void testgetComputedFieldsByFormId() {
        Set<ComputedFieldEntity> computedFieldEntities = computedFieldService.getComputedFieldsByFormId(FORM_ID);

        assertNotNull(computedFieldEntities);
        assertEquals(EXPECTED_COMPUTED_FIELDS_BY_FORM_ID, computedFieldEntities.size());
    }

    @Test
    public void testgetComputedFieldById() {
        ComputedFieldEntity computedFieldEntity = computedFieldService.getComputedFieldById(COMPUTED_FIELD_ID);

        assertNotNull(computedFieldEntity);
        assertEquals(COMPUTED_FIELD_ID, computedFieldEntity.getId());
    }

    @Test
    public void testCreateNewComputedField() {
        createComputedField();
    }

    @Test
    public void testCreateNewComputedFieldFromFormObject() {
        ComputedFieldFormObject computedFieldFormObject = createComputedFieldFormObject();
        computedFieldService.createNewComputedFieldFromFormObject(computedFieldFormObject);

        ComputedFieldEntity computedFieldEntity = null;
        Set<ComputedFieldEntity> computedFieldEntities = computedFieldService.getAllComputedFields();
        for (ComputedFieldEntity computedField : computedFieldEntities) {
            if (COMPUTED_FIELD_NAME == computedField.getName()) {
                computedFieldEntity = computedField;
                break;
            }
        }

        assertNotNull(computedFieldEntity);
        assertEquals(COMPUTED_FIELD_NAME, computedFieldEntity.getName());
    }

    private ComputedFieldEntity createComputedField() {
        FieldEntity fieldEntity = getFieldEntity();
        Set<FieldOperationEntity> fieldOperationEntities = new LinkedHashSet<>(
                createFieldOperationEntities(fieldEntity));
        FormEntity formEntity = formsService.getFormById(FORM_ID);

        assertNotNull(formEntity);
        assertEquals(FORM_ID, formEntity.getId());

        ComputedFieldEntity computedFieldEntity = new ComputedFieldEntity(
                COMPUTED_FIELD_NAME, COMPUTED_FIELD_TYPE, formEntity, fieldOperationEntities);

        computedFieldService.createNewComputedField(computedFieldEntity);
        newComputedFieldId = computedFieldEntity.getId();

        computedFieldEntity = computedFieldService.getComputedFieldById(newComputedFieldId);

        assertNotNull(computedFieldEntity);
        assertEquals(newComputedFieldId, computedFieldEntity.getId());

        return computedFieldEntity;
    }

    private ComputedFieldFormObject createComputedFieldFormObject() {
        FieldEntity fieldEntity = getFieldEntity();
        List<FieldOperationEntity> fieldOperationEntities = createFieldOperationEntities(fieldEntity);

        ComputedFieldFormObject computedFieldFormObject = new ComputedFieldFormObject(
                COMPUTED_FIELD_NAME, COMPUTED_FIELD_TYPE, FORM_ID, fieldOperationEntities);

        return computedFieldFormObject;
    }

    private FieldEntity getFieldEntity() {
        FieldEntity fieldEntity = fieldService.getFieldById(FIELD_ID);

        assertNotNull(fieldEntity);
        assertEquals(FIELD_ID, fieldEntity.getId());

        return fieldEntity;
    }

    private List<FieldOperationEntity> createFieldOperationEntities(FieldEntity fieldEntity) {
        FieldOperationEntity fieldOperationEntity = new FieldOperationEntity(fieldEntity);
        List<FieldOperationEntity> fieldOperationEntities = new ArrayList<>();
        fieldOperationEntities.add(fieldOperationEntity);

        return fieldOperationEntities;
    }
}
