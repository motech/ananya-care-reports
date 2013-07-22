package org.motechproject.carereporting.web.chart.builder;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class BarsBuilderTest {

    private static  final String PARAM_SHOW = "show";
    private static  final String PARAM_HORIZONTAL = "horizontal";
    private static  final String PARAM_SHADOW_SIZE = "shadowSize";
    private static  final String PARAM_BAR_WIDTH = "barWidth";

    private BarsBuilder barsBuilder = new BarsBuilder();

    @Test
    public void testShow() {
        boolean show = true;

        barsBuilder.show(show);

        Map<String, Object> params = (Map<String, Object>) barsBuilder.build();
        assertEquals(show, params.get(PARAM_SHOW));
    }

    @Test
    public void testHorizontal() {
        boolean horizontal = true;

        barsBuilder.horizontal(horizontal);

        Map<String, Object> params = (Map<String, Object>) barsBuilder.build();
        assertEquals(horizontal, params.get(PARAM_HORIZONTAL));
    }

    @Test
    public void testShadowSize() {
        int shadowSize = 2;

        barsBuilder.shadowSize(shadowSize);

        Map<String, Object> params = (Map<String, Object>) barsBuilder.build();
        assertEquals(shadowSize, params.get(PARAM_SHADOW_SIZE));
    }

    @Test
    public void testBarWidth() {
        double barWidth = 3.4;

        barsBuilder.barWidth(barWidth);

        Map<String, Object> params = (Map<String, Object>) barsBuilder.build();
        assertEquals(barWidth, params.get(PARAM_BAR_WIDTH));
    }
}
