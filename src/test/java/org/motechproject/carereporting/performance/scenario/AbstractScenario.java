package org.motechproject.carereporting.performance.scenario;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScenario {

    private List<MockHttpServletRequestBuilder> requests = new ArrayList<>();

    public List<MockHttpServletRequestBuilder> getRequests() {
        return requests;
    }

    protected void addRequest(MockHttpServletRequestBuilder request) {
        requests.add(request);
    }

    protected void addRequests(MockHttpServletRequestBuilder... requests) {
        for (MockHttpServletRequestBuilder request: requests) {
            addRequest(request);
        }
    }
}
