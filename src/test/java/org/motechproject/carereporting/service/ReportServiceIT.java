package org.motechproject.carereporting.service;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class ReportServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    private static final Integer INDICATOR_ID = 1;
    private static final Integer REPORT_TYPE_ID = 1;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ReportService reportService;

    @Before
    public void setupAuthentication() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("CAN_MANAGE_REPORTS"));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("principal", "credentials", authorities));
    }

    @Test
    public void testDefineReport() throws Exception {

        ReportEntity reportEntity = createReport();

        assertEquals(1, reportService.findAllReports().size());

        reportEntity = reportService.findReportById(reportEntity.getId());
        assertNotNull(reportEntity);
    }

    private ReportEntity createReport() {
        return reportService.createNewReport(INDICATOR_ID, REPORT_TYPE_ID);
    }

    @Test
    public void testUpdateReport() throws Exception {
        ReportEntity reportEntity = createReport();

        reportEntity = reportService.findReportById(reportEntity.getId());
        assertNotNull(reportEntity);
    }

    @Test
    public void testDeleteReport() throws Exception {
        ReportEntity reportEntity = createReport();
        reportService.deleteReport(reportEntity);
        assertEquals(0, reportService.findAllReports().size());
    }

}
