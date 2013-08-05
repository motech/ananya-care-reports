package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.ReportService;
import org.motechproject.carereporting.service.UserService;
import org.motechproject.carereporting.web.chart.Chart;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ChartControllerTest {

    @Mock
    private IndicatorService indicatorService;

    @Mock
    private UserService userService;

    @Mock
    private ReportService reportService;

    @Mock
    private UserEntity userEntity;

    @Mock
    private AreaEntity areaEntity;

    @Mock
    private IndicatorEntity indicatorEntity;

    @InjectMocks
    private ChartController chartController = new ChartController();

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(chartController).build();
    }

    @Test
    public void testGetChartData() throws Exception {
        mockMethodCalls();

        mockMvc.perform(get("/api/chart")
                .param("indicatorId", "1")
                .param("chartType", "pie chart")
                .param("startDate", "01/01/2013")
                .param("endDate", "01/02/2013")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

        verify(userService, times(1)).getCurrentlyLoggedUser();
        verify(indicatorService, times(1)).getIndicatorById(anyInt());
        verify(indicatorService, times(1)).getIndicatorValuesForArea(anyInt(), anyInt(),
                (Date) anyObject(), (Date) anyObject());
    }

    @Test
    public void testGetChartValues() throws Exception {
        mockMethodCalls();

        mockMvc.perform(get("/api/chart/data")
                .param("indicatorId", "1")
                .param("startDate", "01/01/2013")
                .param("endDate", "01/02/2013")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).getCurrentlyLoggedUser();
        verify(indicatorService, times(1)).getIndicatorValuesForArea(anyInt(), anyInt(),
                (Date) anyObject(), (Date) anyObject());
    }

    private void mockMethodCalls() {
        when(userService.getCurrentlyLoggedUser()).thenReturn(userEntity);
        when(userEntity.getArea()).thenReturn(areaEntity);
        when(areaEntity.getId()).thenReturn(1);
        when(indicatorService.getIndicatorById(anyInt())).thenReturn(indicatorEntity);
        when(reportService.prepareChart((IndicatorEntity) anyObject(),
                anyString(), anyListOf(IndicatorValueEntity.class))).thenReturn(new Chart());
    }

}
