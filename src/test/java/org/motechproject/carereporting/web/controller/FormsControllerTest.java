package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.initializers.ComputedFieldEntityInitializer;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.motechproject.carereporting.service.FormsService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FormsControllerTest {

    @Mock
    private FormsService formsService;

    @Mock
    private ComputedFieldService computedFieldService;

    @Mock
    private ComputedFieldEntityInitializer formEntityInitializer;

    @InjectMocks
    private FormsController formsController;

    private static final Integer ONE = 1;
    private static final String FORM_TABLE_NAME = "abort_form";
    private static final String COMPUTED_FIELD_NAME = "computed_field_1";
    private static final String FIELD_NAME = "field_1";
    private static final String TABLE_NAME = "table_name_1";
    private static final String FOREIGN_KEY_NAME = "fk_1";
    private static final String TABLE_NAME_1 = "table_1";
    private static final String TABLE_NAME_2 = "table_2";
    private static final String ADD_FORM_JSON_STRING = "{ \"tableName\": \"table_1\", \"displayName\": \"table_1\" }";
    private static final String UPDATE_FORM_JSON_STRING = "{ \"id\": \"1\", \"tableName\": \"table_1\", \"displayName\": \"table_1\" }";

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(formsController).build();
    }

    @Test
    public void testGetAllForms() throws Exception {
        Set<FormEntity> formEntities = new LinkedHashSet<>();
        FormEntity formEntity = new FormEntity();
        formEntity.setId(ONE);
        formEntity.setTableName(FORM_TABLE_NAME);
        formEntities.add(formEntity);
        Mockito.when(formsService.getAllForms()).thenReturn(formEntities);

        mockMvc.perform(get("/api/forms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ONE))
                .andExpect(jsonPath("$[0].tableName").value(FORM_TABLE_NAME));

        verify(formsService, times(1)).getAllForms();
    }

    @Test
    public void testGetForm() throws Exception {
        FormEntity formEntity = new FormEntity();
        formEntity.setId(ONE);
        formEntity.setTableName(FORM_TABLE_NAME);
        Mockito.when(formsService.getFormByIdWithFields(anyInt(), anyString(), anyString())).thenReturn(formEntity);

        mockMvc.perform(get("/api/forms/" + ONE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ONE))
                .andExpect(jsonPath("$.tableName").value(FORM_TABLE_NAME));

        verify(formsService, times(1)).getFormByIdWithFields(anyInt(), anyString(), anyString());
    }

    @Test
    public void testGetComputedFieldsByFormId() throws Exception {
        Set<ComputedFieldEntity> computedFieldEntities = new LinkedHashSet<>();
        ComputedFieldEntity computedFieldEntity = new ComputedFieldEntity();
        computedFieldEntity.setId(ONE);
        computedFieldEntity.setName(COMPUTED_FIELD_NAME);
        computedFieldEntities.add(computedFieldEntity);
        Mockito.when(computedFieldService.getComputedFieldsByFormId(ONE)).thenReturn(computedFieldEntities);

        mockMvc.perform(get("/api/forms/" + ONE + "/computedfields"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ONE))
                .andExpect(jsonPath("$[0].name").value(COMPUTED_FIELD_NAME));

        verify(computedFieldService, times(1)).getComputedFieldsByFormId(ONE);
    }

    @Test
    public void testDeleteForm() throws Exception {
        mockMvc.perform(delete("/api/forms/" + ONE))
                .andExpect(status().isOk());

        verify(formsService, times(1)).deleteFormById(ONE);
    }

    @Test
    public void testGetForeignKeyForTableName() throws Exception {
        Mockito.when(formsService.getForeignKeyForTable(TABLE_NAME)).thenReturn(FOREIGN_KEY_NAME);

        mockMvc.perform(get("/api/forms/table/foreignKey/" + TABLE_NAME))
                .andExpect(status().isOk())
                .andExpect(content().string(FOREIGN_KEY_NAME));

        verify(formsService, times(1)).getForeignKeyForTable(TABLE_NAME);
    }

    @Test
    public void testGetTables() throws Exception {
        Set<String> tables = new LinkedHashSet<>();
        tables.add(TABLE_NAME_1);
        tables.add(TABLE_NAME_2);
        Mockito.when(formsService.getTables()).thenReturn(tables);

        mockMvc.perform(get("/api/forms/tables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(TABLE_NAME_1))
                .andExpect(jsonPath("$[1]").value(TABLE_NAME_2));

        verify(formsService, times(1)).getTables();
    }

    @Test
    public void testAddForm() throws Exception {
        mockMvc.perform(post("/api/forms")
                    .content(ADD_FORM_JSON_STRING)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(formsService, times(1)).addForm((FormEntity)anyObject());
    }

    @Test
    public void testUpdateForm() throws Exception {
        mockMvc.perform(put("/api/forms/" + ONE)
                .content(UPDATE_FORM_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(formsService, times(1)).updateForm((FormEntity) anyObject());
    }

    @Test
    public void testReloadEntitiesFromDB() throws Exception {
        mockMvc.perform(get("/api/forms/reload"))
                .andExpect(status().isOk());

        verify(formEntityInitializer, times(1)).loadFormsFromDB();
    }
}
