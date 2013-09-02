package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.dto.ComputedFieldDto;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ComputedFieldControllerTest {

    private static final String CREATE_COMPUTED_FIELD_JSON = "{\"name\":\"name\",\"fieldOperations\":[],\"form\":\"1\",\"type\":\"String\"}";

    @Mock
    private ComputedFieldService computedFieldService;

    @InjectMocks
    private ComputedFieldController computedFieldController = new ComputedFieldController();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(computedFieldController).build();
    }

    @Test
    public void testGetAllComputedFields() throws Exception {
        Integer id = 1;
        String name = "fieldName";
        ComputedFieldEntity computedFieldEntity = new ComputedFieldEntity();
        computedFieldEntity.setId(id);
        computedFieldEntity.setName(name);

        Set<ComputedFieldEntity> computedFieldEntitySet = new LinkedHashSet<>();
        computedFieldEntitySet.add(computedFieldEntity);

        when(computedFieldService.getAllComputedFields()).thenReturn(computedFieldEntitySet);

        mockMvc.perform(get("/api/computedfields"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name));

        verify(computedFieldService).getAllComputedFields();
    }

    @Test
    public void testGetAllComputedFieldsWithoutOrigin() throws Exception {
        Integer id = 1;
        String name = "fieldName";
        ComputedFieldEntity computedFieldEntity = new ComputedFieldEntity();
        computedFieldEntity.setId(id);
        computedFieldEntity.setName(name);

        Set<ComputedFieldEntity> computedFieldEntitySet = new LinkedHashSet<>();
        computedFieldEntitySet.add(computedFieldEntity);

        when(computedFieldService.getAllComputedFieldsWithoutOrigin()).thenReturn(computedFieldEntitySet);

        mockMvc.perform(get("/api/computedfields/withoutOrigin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name));

        verify(computedFieldService).getAllComputedFieldsWithoutOrigin();
    }

    @Test
    public void testGetComputedField() throws Exception {
        Integer id = 1;
        String name = "fieldName";
        ComputedFieldEntity computedFieldEntity = new ComputedFieldEntity();
        computedFieldEntity.setName(name);
        computedFieldEntity.setId(id);

        when(computedFieldService.getComputedFieldById(id)).thenReturn(computedFieldEntity);

        mockMvc.perform(get("/api/computedfields/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));

        verify(computedFieldService).getComputedFieldById(id);
    }

    @Test
    public void testCreateNewComputedField() throws Exception {
        mockMvc.perform(post("/api/computedfields")
                .content(CREATE_COMPUTED_FIELD_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(computedFieldService).createNewComputedFieldFromDto((ComputedFieldDto) anyObject());
    }
}
