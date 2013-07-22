package org.motechproject.carereporting.web.chart.builder;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class GridBuilderTest {

    private static final String PARAM_VERTICAL_LINES = "verticalLines";
    private static final String PARAM_HORIZONTAL_LINES = "horizontalLines";
    private static final String PARAM_MINOR_VERTICAL_LINES = "minorVerticalLines";

    private GridBuilder gridBuilder = new GridBuilder();

    @Test
    public void testVerticalLines() {
        boolean verticalLines = true;

        gridBuilder.verticalLines(verticalLines);

        Map<String, Object> params = (Map<String, Object>) gridBuilder.build();
        assertEquals(verticalLines, params.get(PARAM_VERTICAL_LINES));
    }

    @Test
    public void testHorizontalLines() {
        boolean horizontalLines = true;

        gridBuilder.horizontalLines(horizontalLines);

        Map<String, Object> params = (Map<String, Object>) gridBuilder.build();
        assertEquals(horizontalLines, params.get(PARAM_HORIZONTAL_LINES));
    }

    @Test
    public void testMinorVerticaLines() {
        boolean minorLines = true;

        gridBuilder.minorVerticalLines(minorLines);

        Map<String, Object> params = (Map<String, Object>) gridBuilder.build();
        assertEquals(minorLines, params.get(PARAM_MINOR_VERTICAL_LINES));
    }
}
