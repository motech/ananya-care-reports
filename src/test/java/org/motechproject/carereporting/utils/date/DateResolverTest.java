package org.motechproject.carereporting.utils.date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.motechproject.carereporting.domain.FrequencyEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateResolverTest {

    private FrequencyEntity frequencyEntity;

    private final Date endDate;
    private final Date startDate;

    private static final String START_DATE_FOR_DAY = "Tue Aug 13 00:00:00 2013";
    private static final String END_DATE_FOR_DAY = "Wed Aug 14 00:00:00 2013";
    private static final String START_DATE_FOR_WEEK = "Mon Aug 05 00:00:00 2013";
    private static final String END_DATE_FOR_WEEK = "Mon Aug 12 00:00:00 2013";
    private static final String START_DATE_FOR_MONTH = "Mon Jul 01 00:00:00 2013";
    private static final String END_DATE_FOR_MONTH = "Thu Aug 01 00:00:00 2013";
    private static final String START_DATE_FOR_QUARTER = "Mon Apr 01 00:00:00 2013";
    private static final String END_DATE_FOR_QUARTER = "Mon Jul 01 00:00:00 2013";
    private static final String START_DATE_FOR_YEAR = "Sun Jan 01 00:00:00 2012";
    private static final String END_DATE_FOR_YEAR = "Tue Jan 01 00:00:00 2013";

    private static final String START_DATE_FOR_DAY_2 = "Mon Mar 12 00:00:00 2012";
    private static final String END_DATE_FOR_DAY_2 = "Thu Aug 15 00:00:00 2013";
    private static final String START_DATE_FOR_WEEK_2 = "Mon Mar 12 00:00:00 2012";
    private static final String END_DATE_FOR_WEEK_2 = "Mon Aug 19 00:00:00 2013";
    private static final String START_DATE_FOR_MONTH_2 = "Thu Mar 01 00:00:00 2012";
    private static final String END_DATE_FOR_MONTH_2 = "Sun Sep 01 00:00:00 2013";
    private static final String START_DATE_FOR_QUARTER_2 = "Sun Jan 01 00:00:00 2012";
    private static final String END_DATE_FOR_QUARTER_2 = "Tue Oct 01 00:00:00 2013";
    private static final String START_DATE_FOR_YEAR_2 = "Sun Jan 01 00:00:00 2012";
    private static final String END_DATE_FOR_YEAR_2 = "Wed Jan 01 00:00:00 2014";

    public DateResolverTest() throws ParseException {
        startDate = DateUtils.parseDate("12.03.2012;13:12", new String[]{"dd.MM.yyyy;HH:mm"});
        endDate = DateUtils.parseDate("14.08.2013;14:41", new String[]{"dd.MM.yyyy;HH:mm"});
        frequencyEntity = new FrequencyEntity();
    }

    private String getStringFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
        return format.format(date);
    }

    @Test
    public void testResolveDatesForComputingWithDailyFrequency() {
        frequencyEntity.setFrequencyName("daily");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, endDate);

        assertEquals(START_DATE_FOR_DAY, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_DAY, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForComputingWithWeeklyFrequency() {
        frequencyEntity.setFrequencyName("weekly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, endDate);

        assertEquals(START_DATE_FOR_WEEK, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_WEEK, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForComputingWithMonthlyFrequency() {
        frequencyEntity.setFrequencyName("monthly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, endDate);

        assertEquals(START_DATE_FOR_MONTH, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_MONTH, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForComputingWithQuarterlyFrequency() {
        frequencyEntity.setFrequencyName("quarterly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, endDate);

        assertEquals(START_DATE_FOR_QUARTER, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_QUARTER, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForComputingWithYearlyFrequency() {
        frequencyEntity.setFrequencyName("yearly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, endDate);

        assertEquals(START_DATE_FOR_YEAR, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_YEAR, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForDisplayingWithDailyFrequency() {
        frequencyEntity.setFrequencyName("daily");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, startDate, endDate);

        assertEquals(START_DATE_FOR_DAY_2, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_DAY_2, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForDisplayingWithWeeklyFrequency() {
        frequencyEntity.setFrequencyName("weekly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, startDate, endDate);

        assertEquals(START_DATE_FOR_WEEK_2, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_WEEK_2, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForDisplayingWithMonthlyFrequency() {
        frequencyEntity.setFrequencyName("monthly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, startDate, endDate);

        assertEquals(START_DATE_FOR_MONTH_2, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_MONTH_2, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForDisplayingWithQuarterlyFrequency() {
        frequencyEntity.setFrequencyName("quarterly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, startDate, endDate);

        assertEquals(START_DATE_FOR_QUARTER_2, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_QUARTER_2, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForDisplayingWithYearlyFrequency() {
        frequencyEntity.setFrequencyName("yearly");

        Date[] dates = DateResolver.resolveDates(frequencyEntity, startDate, endDate);

        assertEquals(START_DATE_FOR_YEAR_2, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_YEAR_2, getStringFromDate(dates[1]));
    }

    @Test
    public void testResolveDatesForInitializerWithDailyFrequency() {
        frequencyEntity.setFrequencyName("daily");

        Date date = DateUtils.addDays(endDate, -1);
        Date[] dates = DateResolver.resolveDates(frequencyEntity, date, date);

        assertEquals(START_DATE_FOR_DAY, getStringFromDate(dates[0]));
        assertEquals(END_DATE_FOR_DAY, getStringFromDate(dates[1]));
    }

}
