package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.dto.IndicatorDto;
import org.motechproject.carereporting.service.IndicatorService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class IndicatorControllerTest {

    private static final String CREATE_INDICATOR_JSON = "{\"values\":[],\"area\":1,\"frequency\":\"30\",\"indicatorType\":3,\"complexCondition\":1,\"name\":\"name\",\"computedField\":399,\"trend\":{\"positiveDiff\":\"5\",\"negativeDiff\":\"-5\"},\"owners\":[1],\"categories\":[1],\"reports\":[{\"reportType\":{\"id\":1,\"name\":\"Bar Chart\"}}]}";
    private static final String CREATE_INDICATOR_JSON_NO_NAME = "{\"values\":[],\"area\":1,\"frequency\":\"30\",\"indicatorType\":3,\"complexCondition\":1,\"computedField\":399,\"trend\":{\"positiveDiff\":\"5\",\"negativeDiff\":\"-5\"},\"owners\":[1],\"categories\":[1],\"reports\":[{\"reportType\":{\"id\":1,\"name\":\"Bar Chart\"}}]}";
    private static final String UPDATE_INDICATOR_JSON = "{\"values\":[],\"area\":1,\"frequency\":30,\"indicatorType\":3,\"complexCondition\":1,\"id\":1,\"name\":\"new name\",\"computedField\":453,\"reports\":[{\"reportType\":{\"id\":3,\"name\":\"Pie Chart\"},\"id\":1}],\"trend\":{\"id\":1,\"positiveDiff\":5,\"negativeDiff\":-5},\"owners\":[1],\"categories\":[2]}";
    private static final String CREATE_CATEGORY_JSON = "{\"name\":\"Name\",\"shortCode\":\"Code\"}";
    private static final String UPDATE_CATEGORY_JSON = "{\"name\":\"New name\",\"shortCode\":\"Code\"}";

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

        Set<IndicatorEntity> indicators = new LinkedHashSet<IndicatorEntity>();
        IndicatorEntity indicator = new IndicatorEntity();
        indicator.setName(indicatorName);
        indicator.setId(indicatorId);
        indicators.add(indicator);
        Mockito.when(indicatorService.getAllIndicators()).thenReturn(indicators);

        mockMvc.perform(get("/api/indicator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(indicatorName))
                .andExpect(jsonPath("$[0].id").value(indicatorId));

        verify(indicatorService, times(1)).getAllIndicators();
    }

    @Test
    public void testGetIndicatorsByCategoryId() throws Exception {
        String indicatorName = "test indicator";
        Integer indicatorId = 1;
        Integer categoryId = 1;

        Set<IndicatorEntity> indicators = new LinkedHashSet<IndicatorEntity>();
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
        Mockito.when(indicatorService.getIndicatorById(indicatorId)).thenReturn(indicator);

        mockMvc.perform(get("/api/indicator/" + indicatorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(indicatorName))
                .andExpect(jsonPath("$.id").value(indicatorId));

        verify(indicatorService, times(1)).getIndicatorById(indicatorId);
    }

    @Test
    public void testCreateIndicator() throws Exception {
        mockMvc.perform(post("/api/indicator")
                .content(CREATE_INDICATOR_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(indicatorService, times(1)).createNewIndicatorFromDto((IndicatorDto) anyObject());
    }

    @Test
    public void testCreateIndicatorWithoutNameValidation() throws Exception {
        mockMvc.perform(post("/api/indicator")
                .content(CREATE_INDICATOR_JSON_NO_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(indicatorService, times(0)).createNewIndicatorFromDto((IndicatorDto) anyObject());
    }

    @Test
    public void testDeleteIndicator() throws Exception {
        Integer indicatorId = 1;
        mockMvc.perform(delete("/api/indicator/" + indicatorId))
                .andExpect(status().isOk());
        verify(indicatorService, times(1)).deleteIndicator((IndicatorEntity) anyObject());
    }

    @Test
    public void testGetIndicatorTypes() throws Exception {
        String indicatorTypeName = "test indicator";
        Integer indicatorTypeId = 1;

        Set<IndicatorTypeEntity> indicatorTypes = new LinkedHashSet<>();
        IndicatorTypeEntity indicatorType = new IndicatorTypeEntity(indicatorTypeName);
        indicatorType.setId(indicatorTypeId);
        indicatorTypes.add(indicatorType);

        Mockito.when(indicatorService.getAllIndicatorTypes()).thenReturn(indicatorTypes);

        mockMvc.perform(get("/api/indicator/type"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(indicatorTypeName))
                .andExpect(jsonPath("$[0].id").value(indicatorTypeId));

        verify(indicatorService, times(1)).getAllIndicatorTypes();
    }

    @Test
    public void testGetIndicatorTypeById() throws Exception {
        String indicatorTypeName = "test indicator";
        Integer indicatorTypeId = 1;
        IndicatorTypeEntity indicatorType = new IndicatorTypeEntity(indicatorTypeName);
        indicatorType.setId(indicatorTypeId);

        Mockito.when(indicatorService.getIndicatorTypeById(indicatorTypeId)).thenReturn(indicatorType);

        mockMvc.perform(get("/api/indicator/type/" + indicatorTypeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(indicatorTypeName))
                .andExpect(jsonPath("$.id").value(indicatorTypeId));

        verify(indicatorService, times(1)).getIndicatorTypeById(indicatorTypeId);
    }

    @Test
    public void testGetIndicatorCategories() throws Exception {
        String categoryName = "indicator category";
        Integer categoryId = 1;

        Set<IndicatorCategoryEntity> categories = new LinkedHashSet<>();
        IndicatorCategoryEntity category = new IndicatorCategoryEntity(categoryName);
        category.setId(categoryId);
        categories.add(category);
        Mockito.when(indicatorService.getAllIndicatorCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/indicator/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(categoryName))
                .andExpect(jsonPath("$[0].id").value(categoryId));

        verify(indicatorService, times(1)).getAllIndicatorCategories();
    }

    @Test
    public void testGetIndicatorCategoryById() throws Exception {
        String categoryName = "indicator category";
        Integer categoryId = 1;

        IndicatorCategoryEntity category = new IndicatorCategoryEntity(categoryName);
        category.setId(categoryId);
        Mockito.when(indicatorService.getIndicatorCategoryById(categoryId)).thenReturn(category);

        mockMvc.perform(get("/api/indicator/category/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryName))
                .andExpect(jsonPath("$.id").value(categoryId));

        verify(indicatorService, times(1)).getIndicatorCategoryById(categoryId);
    }

    @Test
    public void testDeleteIndicatorCategory() throws Exception {
        Integer categoryId = 1;
        mockMvc.perform(delete("/api/indicator/category/" + categoryId))
                .andExpect(status().isOk());
        verify(indicatorService, times(1)).deleteIndicatorCategory((IndicatorCategoryEntity) anyObject());
    }

    @Test
    public void testUpdateIndicator() throws Exception {
        Integer indicatorId = 1;
        mockMvc.perform(put("/api/indicator/" + indicatorId)
            .content(UPDATE_INDICATOR_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(indicatorService, times(1)).updateIndicatorFromDto((IndicatorDto) anyObject());
    }

    @Test
    public void testCreateIndicatorCategory() throws Exception {
        mockMvc.perform(put("/api/indicator/category")
                .content(CREATE_CATEGORY_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(indicatorService, times(1)).createNewIndicatorCategory((IndicatorCategoryEntity) anyObject());
    }

    @Test
    public void testUpdateIndicatorCategory() throws Exception {
        Integer categoryId = 1;
        String categoryName = "name";
        IndicatorCategoryEntity indicatorCategory = new IndicatorCategoryEntity(categoryName);
        Mockito.when(indicatorService.getIndicatorCategoryById(categoryId)).thenReturn(indicatorCategory);
        mockMvc.perform(put("/api/indicator/category/" + categoryId)
                .content(UPDATE_CATEGORY_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(indicatorService, times(1)).updateIndicatorCategory(indicatorCategory);
    }

}
