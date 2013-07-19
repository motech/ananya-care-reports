package org.motechproject.carereporting.web.chart.builder;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LegendBuilderTest {

    private static final String PARAM_POSITION = "position";
    private static final String PARAM_BACKGROUND_COLOR = "backgroundColor";

    private LegendBuilder legendBuilder = new LegendBuilder();

    @Test
    public void testPosition() {
        LegendBuilder.Position position = LegendBuilder.Position.BOTTOM_LEFT;

        legendBuilder.position(position);

        Map<String, Object> params = (Map<String, Object>) legendBuilder.build();
        assertEquals(position.getValue(), params.get(PARAM_POSITION));
    }

    @Test
    public void testBackgroundColor() {
        String color = "red";

        legendBuilder.backgroundColor(color);

        Map<String, Object> params = (Map<String, Object>) legendBuilder.build();
        assertEquals(color, params.get(PARAM_BACKGROUND_COLOR));
    }

}
