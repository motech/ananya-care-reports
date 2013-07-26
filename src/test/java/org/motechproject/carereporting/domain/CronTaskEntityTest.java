package org.motechproject.carereporting.domain;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class CronTaskEntityTest {

    @Test
    public void testToCronExpressionWithoutYear() throws ParseException {
        String expression = "* 0 1 2 L ?";
        CronTaskEntity cronTaskEntity = new CronTaskEntity();
        cronTaskEntity.setExpression(expression);

        assertEquals(expression, cronTaskEntity.toCronExpression());
    }

    @Test
    public void testToCronExpressionWithYear() throws ParseException {
        String expression = "* 0 1 2 L ? 2000";
        CronTaskEntity cronTaskEntity = new CronTaskEntity();
        cronTaskEntity.setExpression(expression);

        assertEquals(expression, cronTaskEntity.toCronExpression());
    }

    @Test(expected = ParseException.class)
    public void testSetExpression() throws ParseException {
        String expression = "* 0 1 2 3 *";
        CronTaskEntity cronTaskEntity = new CronTaskEntity();
        cronTaskEntity.setExpression(expression);
    }

}
