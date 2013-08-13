package org.motechproject.carereporting.utils.date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.motechproject.carereporting.domain.FrequencyEntity;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateResolverTest {

    private FrequencyEntity frequencyEntity;

    private final Date END_DATE;
    private final Date START_DATE;

    private final String START_DATE_FOR_DAY = "Tue Aug 13 00:00:00 CEST 2013";
    private final String END_DATE_FOR_DAY = "Wed Aug 14 00:00:00 CEST 2013";
    private final String START_DATE_FOR_WEEK = "Mon Aug 05 00:00:00 CEST 2013";
    private final String END_DATE_FOR_WEEK = "Mon Aug 12 00:00:00 CEST 2013";
    private final String START_DATE_FOR_MONTH = "Mon Jul 01 00:00:00 CEST 2013";
    private final String END_DATE_FOR_MONTH = "Thu Aug 01 00:00:00 CEST 2013";
    private final String START_DATE_FOR_QUARTER = "Mon Apr 01 00:00:00 CEST 2013";
    private final String END_DATE_FOR_QUARTER = "Mon Jul 01 00:00:00 CEST 2013";
    private final String START_DATE_FOR_YEAR = "Sun Jan 01 00:00:00 CET 2012";
    private final String END_DATE_FOR_YEAR = "Tue Jan 01 00:00:00 CET 2013";

    private final String START_DATE_FOR_DAY_2 = "Mon Mar 12 00:00:00 CET 2012";
    private final String END_DATE_FOR_DAY_2 = "Thu Aug 15 00:00:00 CEST 2013";
    private final String START_DATE_FOR_WEEK_2 = "Mon Mar 12 00:00:00 CET 2012";
    private final String END_DATE_FOR_WEEK_2 = "Mon Aug 19 00:00:00 CEST 2013";
    private final String START_DATE_FOR_MONTH_2 = "Thu Mar 01 00:00:00 CET 2012";
    private final String END_DATE_FOR_MONTH_2 = "Sun Sep 01 00:00:00 CEST 2013";
    private final String START_DATE_FOR_QUARTER_2 = "Sun Jan 01 00:00:00 CET 2012";
    private final String END_DATE_FOR_QUARTER_2 = "Tue Oct 01 00:00:00 CEST 2013";
    private final String START_DATE_FOR_YEAR_2 = "Sun Jan 01 00:00:00 CET 2012";
    private final String END_DATE_FOR_YEAR_2 = "Wed Jan 01 00:00:00 CET 2014";

    public DateResolverTest() throws ParseException {
        START_DATE = DateUtils.parseDate("12.03.2012;13:12", new String[]{"dd.MM.yyyy;HH:mm"});
        END_DATE = DateUtils.parseDate("14.08.2013;14:41", new String[]{"dd.MM.yyyy;HH:mm"});
        frequencyEntity = new FrequencyEntity();
    }

    @Test
    public void testResolveDatesForComputingWithDailyFrequency() {
        frequencyEntity.setFrequencyName("daily");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, END_DATE);

        assertEquals(START_DATE_FOR_DAY, dates[0].toString());
        assertEquals(END_DATE_FOR_DAY, dates[1].toString());
    }

    @Test
    public void testResolveDatesForComputingWithWeeklyFrequency() {
        frequencyEntity.setFrequencyName("weekly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, END_DATE);

        assertEquals(START_DATE_FOR_WEEK, dates[0].toString());
        assertEquals(END_DATE_FOR_WEEK, dates[1].toString());
    }

    @Test
    public void testResolveDatesForComputingWithMonthlyFrequency() {
        frequencyEntity.setFrequencyName("monthly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, END_DATE);

        assertEquals(START_DATE_FOR_MONTH, dates[0].toString());
        assertEquals(END_DATE_FOR_MONTH, dates[1].toString());
    }

    @Test
    public void testResolveDatesForComputingWithQuarterlyFrequency() {
        frequencyEntity.setFrequencyName("quarterly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, END_DATE);

        assertEquals(START_DATE_FOR_QUARTER, dates[0].toString());
        assertEquals(END_DATE_FOR_QUARTER, dates[1].toString());
    }

    @Test
    public void testResolveDatesForComputingWithYearlyFrequency() {
        frequencyEntity.setFrequencyName("yearly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, END_DATE);

        assertEquals(START_DATE_FOR_YEAR, dates[0].toString());
        assertEquals(END_DATE_FOR_YEAR, dates[1].toString());
    }

    @Test
    public void testResolveDatesForDisplayingWithDailyFrequency() {
        frequencyEntity.setFrequencyName("daily");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, START_DATE, END_DATE);

        assertEquals(START_DATE_FOR_DAY_2, dates[0].toString());
        assertEquals(END_DATE_FOR_DAY_2, dates[1].toString());
    }

    @Test
    public void testResolveDatesForDisplayingWithWeeklyFrequency() {
        frequencyEntity.setFrequencyName("weekly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, START_DATE, END_DATE);

        assertEquals(START_DATE_FOR_WEEK_2, dates[0].toString());
        assertEquals(END_DATE_FOR_WEEK_2, dates[1].toString());
    }

    @Test
    public void testResolveDatesForDisplayingWithMonthlyFrequency() {
        frequencyEntity.setFrequencyName("monthly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, START_DATE, END_DATE);

        assertEquals(START_DATE_FOR_MONTH_2, dates[0].toString());
        assertEquals(END_DATE_FOR_MONTH_2, dates[1].toString());
    }

    @Test
    public void testResolveDatesForDisplayingWithQuarterlyFrequency() {
        frequencyEntity.setFrequencyName("quarterly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, START_DATE, END_DATE);

        assertEquals(START_DATE_FOR_QUARTER_2, dates[0].toString());
        assertEquals(END_DATE_FOR_QUARTER_2, dates[1].toString());
    }

    @Test
    public void testResolveDatesForDisplayingWithYearlyFrequency() {
        frequencyEntity.setFrequencyName("yearly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, START_DATE, END_DATE);

        assertEquals(START_DATE_FOR_YEAR_2, dates[0].toString());
        assertEquals(END_DATE_FOR_YEAR_2, dates[1].toString());
    }

    @Test
    public void testResolveDatesForInitializerWithDailyFrequency() {
        frequencyEntity.setFrequencyName("daily");

        Date date = DateUtils.addDays(END_DATE, -1);
        Date[] dates = DateResolver.resolveDates(frequencyEntity, date, date);

        assertEquals(START_DATE_FOR_DAY, dates[0].toString());
        assertEquals(END_DATE_FOR_DAY, dates[1].toString());
    }

}
