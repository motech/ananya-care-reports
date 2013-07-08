package org.motechproject.carereporting.service;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class ReportServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String REPORT_NAME = "name";
    private static final String NEW_REPORT_NAME = "newName";

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

        ReportEntity reportEntity = createReport(REPORT_NAME);

        assertEquals(1, reportService.findAllReports().size());

        reportEntity = reportService.findReportById(reportEntity.getId());
        assertEquals(REPORT_NAME, reportEntity.getName());
    }

    private ReportEntity createReport(String name) {
        return reportService.createNewReport(name, INDICATOR_ID, REPORT_TYPE_ID);
    }

    @Test(expected = EntityException.class)
    public void testCreateReportNameTakenError() throws Exception {
        createReport(REPORT_NAME);
        createReport(REPORT_NAME);
    }

    @Test
    public void testUpdateReport() throws Exception {
        ReportEntity reportEntity = createReport(REPORT_NAME);
        updateReportName(reportEntity.getId(), NEW_REPORT_NAME);

        reportEntity = reportService.findReportById(reportEntity.getId());
        assertEquals(NEW_REPORT_NAME, reportEntity.getName());
    }

    private void updateReportName(Integer reportId, String newName) {
        reportService.updateReport(reportId, newName, INDICATOR_ID, REPORT_TYPE_ID);
    }

    @Test
    public void testDeleteReport() throws Exception {
        ReportEntity reportEntity = createReport(REPORT_NAME);
        reportService.deleteReport(reportEntity);
        assertEquals(0, reportService.findAllReports().size());
    }

}
