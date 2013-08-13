package org.motechproject.carereporting.initializers;

import org.apache.commons.lang.time.DateUtils;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.indicator.IndicatorValueCalculator;
import org.motechproject.carereporting.service.CronService;
import org.motechproject.carereporting.utils.date.DateResolver;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

public class IndicatorValuesInitializer {

    @Autowired
    private CronService cronService;

    @Autowired
    private IndicatorValueCalculator indicatorValueCalculator;

    public void precomputeIndicators() throws ParseException {
        //TODO: get the earliest date from report database
        Date startDate = DateUtils.parseDate("01/09/2011/06", new String[]{"dd/MM/yyyy/HH"});
        Date endDate = new Date();

        Set<FrequencyEntity> frequencyEntities = cronService.getAllFrequencies();

        for (FrequencyEntity frequencyEntity: frequencyEntities) {
            Date date = new Date(startDate.getTime());

            while (date.before(endDate)) {
                Date[] dates = DateResolver.resolveDates(frequencyEntity, date, date);
                indicatorValueCalculator.calculateIndicatorValues(frequencyEntity);
                date = DateUtils.addHours(dates[1], 6);
            }
        }
    }
}
