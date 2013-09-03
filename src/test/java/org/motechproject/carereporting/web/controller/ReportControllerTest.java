package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.ReportTypeEntity;
import org.motechproject.carereporting.domain.dto.ReportDto;
import org.motechproject.carereporting.service.ReportService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ReportControllerTest {

    private static final String SAVE_REPORT_JSON = "{\"indicatorId\":\"1\", \"reportTypeId\":\"1\"}";
    private static final String UPDATE_REPORT_JSON = "{\"indicatorId\":\"1\", \"reportTypeId\":\"1\", \"labelX\":\"Label X\", \"labelY\":\"Label Y\"}";

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
    public void testSaveReport() throws Exception {
        mockMvc.perform(post("/api/report")
                .content(SAVE_REPORT_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(reportService, times(1)).createNewReport(anyInt(), anyInt());
    }

    @Test
    public void testDeleteReport() throws Exception {
        Integer reportId = 1;
        mockMvc.perform(delete("/api/report/" + reportId))
                .andExpect(status().isOk());

        verify(reportService, times(1)).deleteReportById(reportId);
    }

    @Test
    public void testGetAllReportTypes() throws Exception {
        Integer id = 1;
        String reportName = "reportName";
        ReportTypeEntity reportTypeEntity = new ReportTypeEntity(reportName);
        reportTypeEntity.setId(id);

        Set<ReportTypeEntity> entitySet = new LinkedHashSet<>();
        entitySet.add(reportTypeEntity);
        when(reportService.getAllReportTypes()).thenReturn(entitySet);

        mockMvc.perform(get("/api/report/type"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(reportName));

        verify(reportService).getAllReportTypes();
    }

    @Test
    public void testGetReportById() throws Exception {
        Integer id = 1;
        String reportName = "reportName";
        ReportTypeEntity reportTypeEntity = new ReportTypeEntity(reportName);
        reportTypeEntity.setId(id);
        ReportEntity reportEntity = new ReportEntity(reportTypeEntity);
        reportEntity.setId(id);

        when(reportService.getReportById(id)).thenReturn(reportEntity);

        mockMvc.perform(get("/api/report/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.reportType.id").value(id))
                .andExpect(jsonPath("$.reportType.name").value(reportName));

        verify(reportService).getReportById(id);
    }

    @Test
    public void testGetAllReports() throws Exception {
        Integer id = 1;
        String reportName = "reportName";
        ReportTypeEntity reportTypeEntity = new ReportTypeEntity(reportName);
        reportTypeEntity.setId(id);
        ReportEntity reportEntity = new ReportEntity(reportTypeEntity);
        reportEntity.setId(id);

        Set<ReportEntity> reportEntities = new LinkedHashSet<>();
        reportEntities.add(reportEntity);

        when(reportService.getAllReports()).thenReturn(reportEntities);

        mockMvc.perform(get("/api/report/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].reportType.id").value(id))
                .andExpect(jsonPath("$[0].reportType.name").value(reportName));

        verify(reportService).getAllReports();
    }

    @Test
    public void testUpdateReport() throws Exception {
        Integer id = 1;

        mockMvc.perform(put("/api/report/" + id)
                .content(UPDATE_REPORT_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(reportService).updateReport((ReportDto) anyObject());
    }
}

