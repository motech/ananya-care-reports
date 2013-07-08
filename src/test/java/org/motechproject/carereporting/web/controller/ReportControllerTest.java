package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.exception.EntityException;
import org.motechproject.carereporting.service.ReportService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ReportControllerTest {

    private static final String DEFINE_REPORT_JSON = "{\"name\":\"name\", \"indicatorId\":\"1\", \"reportTypeId\":\"1\"}";
    private static final String DEFINE_REPORT_JSON_NO_REPORT_NAME = "{\"indicatorId\":\"1\", \"reportTypeId\":\"1\"}";

    @Mock
	private ReportService reportService;
	
	@InjectMocks
	private ReportController reportController = new ReportController();
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
	}

    @Test
    public void testDefineReport() throws Exception {
        mockMvc.perform(put("/api/report")
                .content(DEFINE_REPORT_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(reportService, times(1)).createNewReport(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testDefineReportNoNameParam() throws Exception {
        mockMvc.perform(put("/api/report")
                .content(DEFINE_REPORT_JSON_NO_REPORT_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(reportService, times(0)).createNewReport(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testDefineReportDuplicateName() throws Exception {
        doThrow(new EntityException()).when(reportService).createNewReport(anyString(), anyInt(), anyInt());

        mockMvc.perform(put("/api/report")
                .content(DEFINE_REPORT_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(reportService, times(1)).createNewReport(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testDeleteReport() throws Exception {
        Integer reportId = 1;
        mockMvc.perform(delete("/api/report/" + reportId))
                .andExpect(status().isOk());

        verify(reportService, times(1)).deleteReportById(reportId);
    }

}

