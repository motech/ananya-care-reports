package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.exception.CareResourceNotFoundRuntimeException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ErrorControllerTest {

    private static final Integer ID = 1;
    private static final Integer BAD_REQUEST_STATUS_CODE = 400;
    private static final String REQUEST_URI = "Unknown";

    private MockMvc mockMvc;

    @Mock
    private ErrorController errorController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(errorController).build();
    }

    @Test
    public void testGetErrorPage() throws Exception {
        CareResourceNotFoundRuntimeException careResourceNotFoundRuntimeException =
                new CareResourceNotFoundRuntimeException(IndicatorEntity.class, ID, new Exception("message"));

        mockMvc.perform(get("/error")
                .requestAttr("javax.servlet.error.status_code", BAD_REQUEST_STATUS_CODE)
                .requestAttr("javax.servlet.error.exception", careResourceNotFoundRuntimeException)
                .requestAttr("javax.servlet.error.request_uri", REQUEST_URI))
                .andExpect(status().isOk());

        verify(errorController, times(1)).getErrorPage((HttpServletRequest)anyObject());
    }
}
