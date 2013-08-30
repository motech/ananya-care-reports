package org.motechproject.carereporting.performance.scenario;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Vector;

public abstract class AbstractScenario {

    private List<MockHttpServletRequestBuilder> requests = new Vector<>();

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

    protected void addRequests(AbstractScenario... scenarios) {
        for (AbstractScenario scenario : scenarios) {
            for (MockHttpServletRequestBuilder request : scenario.getRequests()) {
                addRequest(request);
            }
        }
    }
}
