package org.motechproject.carereporting.performance.helpers;

import org.apache.log4j.Logger;
import org.motechproject.carereporting.performance.PhasePT;

public class StatisticsHelper {

    private static final Logger LOGGER = Logger.getLogger(StatisticsHelper.class);

    private StatisticsHelper() {
    }

    public static void printHeader(PhasePT phase) {
        LOGGER.info("Phase: " + phase.getClass().getSimpleName());
        LOGGER.info("Report lookers: " + phase.getReportLookers());
        LOGGER.info("Avg flw req / sec: " + phase.getAvgReqPerSec());
        LOGGER.info("Peak req / sec: " + PhasePT.PEEK_COEFFICIENT * phase.getAvgReqPerSec());
    }

    public static void printStart(Runnable runnable) {
        LOGGER.info(runnable.getClass().getSimpleName() + " started");
    }

    public static void printEnd(Runnable runnable) {
        LOGGER.info(runnable.getClass().getSimpleName() + " finished");
    }

    public static void printTime(long time) {
        LOGGER.info("Time elapsed: " + time + "ms");
    }

}
