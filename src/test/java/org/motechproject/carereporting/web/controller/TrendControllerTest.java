package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TrendControllerTest {

    private static final String GET_TRENDS_START_DATE = "23/05/2013";

    private static final String GET_TRENDS_END_DATE = "25/06/2013";

    @Mock
    private IndicatorService indicatorService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TrendController trendController = new TrendController();

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(trendController).build();
    }

    @Test
    public void testGetTrends() throws Exception {
        UserEntity userEntity = new UserEntity();
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setId(2);
        userEntity.setArea(areaEntity);

        when(userService.getCurrentlyLoggedUser()).thenReturn(userEntity);
        mockMvc.perform(get("/api/trend")
                .param("startDate", GET_TRENDS_START_DATE)
                .param("endDate", GET_TRENDS_END_DATE)
                .param("areaId", "1")
                .param("frequencyId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).getCurrentlyLoggedUser();
        verify(indicatorService, times(1)).getIndicatorsWithTrendsUnderUser((UserEntity) anyObject(),
                (Date) anyObject(), (Date) anyObject(), anyInt(), anyInt());
    }

}
