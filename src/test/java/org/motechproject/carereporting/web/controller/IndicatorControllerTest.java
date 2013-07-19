package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class IndicatorControllerTest {

    @Mock
    private IndicatorService indicatorService;
	
	@InjectMocks
	private IndicatorController indicatorController = new IndicatorController();
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(indicatorController).build();
	}

    @Test
    public void testGetIndicators() throws Exception {
        String indicatorName = "test indicator";
        Integer indicatorId = 1;

        Set<IndicatorEntity> indicators = new HashSet<IndicatorEntity>();
        IndicatorEntity indicator = new IndicatorEntity();
        indicator.setName(indicatorName);
        indicator.setId(indicatorId);
        indicators.add(indicator);
        Mockito.when(indicatorService.findAllIndicators()).thenReturn(indicators);

        mockMvc.perform(get("/api/indicator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(indicatorName))
                .andExpect(jsonPath("$[0].id").value(indicatorId));

        verify(indicatorService, times(1)).findAllIndicators();
    }

    @Test
    public void testGetIndicatorsByCategoryId() throws Exception {
        String indicatorName = "test indicator";
        Integer indicatorId = 1;
        Integer categoryId = 1;

        Set<IndicatorEntity> indicators = new HashSet<IndicatorEntity>();
        IndicatorEntity indicator = new IndicatorEntity();
        indicator.setName(indicatorName);
        indicator.setId(indicatorId);
        indicators.add(indicator);
        Mockito.when(indicatorService.getIndicatorsByCategoryId(categoryId)).thenReturn(indicators);

        mockMvc.perform(get("/api/indicator/filter/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(indicatorName))
                .andExpect(jsonPath("$[0].id").value(indicatorId));

        verify(indicatorService, times(1)).getIndicatorsByCategoryId(categoryId);
    }

    @Test
    public void testGetIndicatorById() throws Exception {
        String indicatorName = "test indicator";
        Integer indicatorId = 1;

        IndicatorEntity indicator = new IndicatorEntity();
        indicator.setName(indicatorName);
        indicator.setId(indicatorId);
        Mockito.when(indicatorService.findIndicatorById(indicatorId)).thenReturn(indicator);

        mockMvc.perform(get("/api/indicator/" + indicatorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(indicatorName))
                .andExpect(jsonPath("$.id").value(indicatorId));

        verify(indicatorService, times(1)).findIndicatorById(indicatorId);
    }

}
