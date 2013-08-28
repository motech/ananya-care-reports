package org.motechproject.carereporting.performance;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.performance.helpers.StatisticsHelper;
import org.motechproject.carereporting.performance.scenario.PerformanceSummaryScenario;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public abstract class PhasePT {

    public static final int PEEK_COEFFICIENT = 4;
    public static final int PARTS = 5;
    private static final int MILLISECONDS = 1000;

    private int reportLookers;
    private double avgReqPerSec;
    private double avgWaitTime;
    private double peekWaitTime;
    private Thread[] users;

    protected PhasePT(int reportLookers, double avgReqPerSec) {
        this.reportLookers = reportLookers;
        this.avgReqPerSec = avgReqPerSec;
        avgWaitTime = MILLISECONDS / avgReqPerSec;
        peekWaitTime = avgWaitTime / PEEK_COEFFICIENT;
        users = new Thread[reportLookers];
        StatisticsHelper.printHeader(this);
    }

    public int getReportLookers() {
        return reportLookers;
    }

    public double getAvgReqPerSec() {
        return avgReqPerSec;
    }

    private void runTest(Class<PerformanceSummaryScenario> runnable) throws InterruptedException, IllegalAccessException, InstantiationException {
        StatisticsHelper.printStart(runnable.newInstance());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < reportLookers; i++) {
            users[i] = new Thread(runnable.newInstance());
            users[i].start();
            if (i > (PARTS / 2) * reportLookers / PARTS && i < (PARTS / 2 + 1)* reportLookers / PARTS ) {
                Thread.sleep((long) peekWaitTime);
            } else {
                Thread.sleep((long) avgWaitTime);
            }
        }
        for (int i = 0; i < reportLookers; i++) {
            users[i].join();
        }

        stopWatch.stop();
        StatisticsHelper.printEnd(runnable.newInstance());
        StatisticsHelper.printTime(stopWatch.getTime());
    }

    @Test
    public void testPerformanceDashboard() throws InterruptedException, InstantiationException, IllegalAccessException {
        runTest(PerformanceSummaryScenario.class);
    }
}
