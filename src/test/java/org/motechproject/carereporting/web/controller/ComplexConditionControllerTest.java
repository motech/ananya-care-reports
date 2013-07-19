package org.motechproject.carereporting.web.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.dto.DashboardPositionDto;
import org.motechproject.carereporting.service.ComplexConditionService;
import org.motechproject.carereporting.service.DashboardService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ComplexConditionControllerTest {

    private static final String TEST_COMPLEX_CONDITION_ID = "1";

    private static final String TEST_COMPLEX_CONDITION_NAME = "test_complex_condition";

    @Mock
    private ComplexConditionService complexConditionService;

    @InjectMocks
    private ComplexConditionController complexConditionController = new ComplexConditionController();

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(complexConditionController).build();
    }

    @Test
    public void testGetComplexConditionList() throws Exception {
        mockMvc.perform(get("/api/complexcondition")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(complexConditionService, times(1)).getAllComplexConditions();
    }

    @Test
    public void testGetOperatorTypeList() throws Exception {
        mockMvc.perform(get("/api/complexcondition/operatortype")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(complexConditionService, times(1)).getAllOperatorTypes();
    }

    @Test
    public void testGetComparisonSymbolList() throws Exception {
        mockMvc.perform(get("/api/complexcondition/comparisonsymbol")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(complexConditionService, times(1)).getAllComparisonSymbols();
    }

    @Test
    public void testGetComplexConditionById() throws Exception {
        ComplexConditionEntity complexCondition = new ComplexConditionEntity();
        complexCondition.setName(TEST_COMPLEX_CONDITION_NAME);

        when(complexConditionService.getComplexConditionById(anyInt())).
                thenReturn(complexCondition);

        mockMvc.perform(get("/api/complexcondition/" + TEST_COMPLEX_CONDITION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(TEST_COMPLEX_CONDITION_NAME));

        verify(complexConditionService, times(1)).getComplexConditionById(anyInt());
    }

}
