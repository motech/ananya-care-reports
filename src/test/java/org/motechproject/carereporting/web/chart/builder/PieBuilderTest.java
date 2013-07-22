package org.motechproject.carereporting.web.chart.builder;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PieBuilderTest {

    private static final String PARAM_SHOW = "show";
    private static final String PARAM_EXPLODE = "explode";

    private PieBuilder pieBuilder = new PieBuilder();

    @Test
    public void testShow() {
        boolean show = false;

        pieBuilder.show(show);

        Map<String, Object> params = (Map<String, Object>) pieBuilder.build();
        assertEquals(show, params.get(PARAM_SHOW));
    }

    @Test
    public void testExplode() {
        int explode = 9;

        pieBuilder.explode(explode);

        Map<String, Object> params = (Map<String, Object>) pieBuilder.build();
        assertEquals(explode, params.get(PARAM_EXPLODE));
    }
}
