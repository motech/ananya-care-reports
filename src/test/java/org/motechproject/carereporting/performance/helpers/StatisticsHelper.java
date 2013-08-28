package org.motechproject.carereporting.performance.helpers;

import org.apache.log4j.Logger;
import org.motechproject.carereporting.performance.PhasePT;

public class StatisticsHelper {

    private static final Logger LOGGER = Logger.getLogger(StatisticsHelper.class);

    private StatisticsHelper() {
    }

    public static void printStart(Object object) {
        LOGGER.info(object.getClass().getSimpleName() + " started");
    }

    public static void printEnd(Object object) {
        LOGGER.info(object.getClass().getSimpleName() + " finished");
    }

    public static void printTime(long time) {
        LOGGER.info("Time elapsed: " + time + "ms");
    }

}
