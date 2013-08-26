package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.dto.DashboardPositionDto;
import org.motechproject.carereporting.service.DashboardService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerTest {

    private static final String SAVE_POSITIONS_JSON =
            "[{\"position\":0,\"name\":\"Map report\"},{\"position\":1,\"name\":\"Performance summary\"}]";

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController = new DashboardController();

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();
    }

    @Test
    public void testGetAllDashboards() throws Exception {
        mockMvc.perform(get("/api/dashboards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(dashboardService, times(1)).getAllDashboards();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSaveDashboardPositions() throws Exception {
        mockMvc.perform(post("/api/dashboards/save-positions")
                .content(SAVE_POSITIONS_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(dashboardService, times(1)).saveDashboardsPositions((List<DashboardPositionDto>) anyObject());
    }

}
