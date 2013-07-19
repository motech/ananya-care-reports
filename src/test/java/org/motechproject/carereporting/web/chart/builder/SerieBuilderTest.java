package org.motechproject.carereporting.web.chart.builder;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class SerieBuilderTest {

    private static double delta = 1e-15;

    private SerieBuilder serieBuilder;

    @Before
    public void clean() {
        serieBuilder = new SerieBuilder();
    }

    @Test
    public void testLabel() {
        String label = "label1";

        serieBuilder.label(label);

        assertEquals(label, serieBuilder.build().getLabel());
    }

    @Test
    public void testPointWithBigDecimalAndDouble() {
        BigDecimal x = new BigDecimal(23);
        double y = 9.8;

        serieBuilder.point(x, y);

        assertEquals(1, serieBuilder.build().getData().length);
        assertEquals(x, serieBuilder.build().getData()[0][0]);
        assertEquals(y, serieBuilder.build().getData()[0][1].doubleValue(), delta);
    }

    @Test
    public void testPointWithDoubles() {
        double x = 12.3;
        double y = 9.8;

        serieBuilder.point(x, y);

        assertEquals(1, serieBuilder.build().getData().length);
        assertEquals(x, serieBuilder.build().getData()[0][0].doubleValue(), delta);
        assertEquals(y, serieBuilder.build().getData()[0][1].doubleValue(), delta);
    }

    @Test
    public void testPointWithBigDecimalAndInteger() {
        BigDecimal x = new BigDecimal(4.1);
        Integer y = new Integer(12);

        serieBuilder.point(x, y);

        assertEquals(1, serieBuilder.build().getData().length);
        assertEquals(x, serieBuilder.build().getData()[0][0]);
        assertEquals(y.intValue(), serieBuilder.build().getData()[0][1].intValue());
    }

    @Test
    public void testPointWithIntegers() {
        Integer x = new Integer(9);
        Integer y = new Integer(12);

        serieBuilder.point(x, y);

        assertEquals(1, serieBuilder.build().getData().length);
        assertEquals(x.intValue(), serieBuilder.build().getData()[0][0].intValue());
        assertEquals(y.intValue(), serieBuilder.build().getData()[0][1].intValue());
    }

    @Test
    public void testPointWithBigDecimals() {
        BigDecimal x = new BigDecimal(1.2);
        BigDecimal y = new BigDecimal(2.3);

        serieBuilder.point(x, y);

        assertEquals(1, serieBuilder.build().getData().length);
        assertEquals(x, serieBuilder.build().getData()[0][0]);
        assertEquals(y, serieBuilder.build().getData()[0][1]);
    }
}
