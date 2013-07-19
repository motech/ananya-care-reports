package org.motechproject.carereporting.web.chart;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = "classpath:testContext.xml")
public class SerieTest {

    @Test
    public void testGetData() {
        BigDecimal key = new BigDecimal(23);
        BigDecimal value = new BigDecimal(1);
        Map<BigDecimal, BigDecimal> data = new HashMap<>();
        data.put(key, value);
        Serie serie = new Serie();
        serie.setData(data);

        assertEquals(1, serie.getData().length);
        assertEquals(key, serie.getData()[0][0]);
        assertEquals(value, serie.getData()[0][1]);
    }

}
