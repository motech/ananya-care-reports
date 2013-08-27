package org.motechproject.carereporting.performance;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.performance.scenario.PerformanceSummaryScenario;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public abstract class AbstractPT implements PerformanceTest {

    private static final int PEEK_COEFFICIENT = 4;
    private final Logger LOG = Logger.getLogger(getClass());

    private int reportLookers;
    private double avgReqPerSec;
    private Thread[] users;

    protected AbstractPT(int reportLookers, double avgReqPerSec) {
        this.reportLookers = reportLookers;
        this.avgReqPerSec = avgReqPerSec;
        users = new Thread[reportLookers];
    }

    private void runTest(Runnable runnable) throws InterruptedException {
        for (int i = 0; i < reportLookers; i++) {
            users[i] = new Thread(runnable);
            users[i].start();
        }
        for (int i = 0; i < reportLookers; i++) {
            users[i].join();
        }
    }

    @Override
    @Test
    public void testPerformanceDashboard() throws InterruptedException {
        runTest(new PerformanceSummaryScenario());
    }
}
