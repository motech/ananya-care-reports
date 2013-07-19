package org.motechproject.carereporting.web.chart.builder;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AxisBuilderTest {

    private static final String PARAM_SHOW_LABELS = "showLabels";
    private static final String PARAM_MINOR_TICK_FREQ = "minorTickFreq";
    private static final String PARAM_MIN = "min";
    private static final String PARAM_AUTO_SCALE_MARGIN = "autoScaleMargin";
    private static final String PARAM_MODE = "mode";
    private static final String PARAM_TIMEFORMAT = "timeformat";
    private static final String PARAM_SCALING = "scaling";

    private AxisBuilder axisBuilder = new AxisBuilder();

    @Test
    public void testShowLabels() {
        boolean labels = true;

        axisBuilder.showLabels(labels);

        Map<String, Object> params = (Map<String, Object>) axisBuilder.build();
        assertEquals(labels, params.get(PARAM_SHOW_LABELS));
    }

    @Test
    public void testMinorTickFreq() {
        int tickFreq = 20;

        axisBuilder.minorTickFreq(tickFreq);

        Map<String, Object> params = (Map<String, Object>) axisBuilder.build();
        assertEquals(tickFreq, params.get(PARAM_MINOR_TICK_FREQ));
    }

    @Test
    public void testMin() {
        int min = -3;

        axisBuilder.min(min);

        Map<String, Object> params = (Map<String, Object>) axisBuilder.build();
        assertEquals(min, params.get(PARAM_MIN));
    }

    @Test
    public void testAutoScaleMargin() {
        int margin = 43;

        axisBuilder.autoScaleMargin(margin);

        Map<String, Object> params = (Map<String, Object>) axisBuilder.build();
        assertEquals(margin, params.get(PARAM_AUTO_SCALE_MARGIN));
    }

    @Test
    public void testMode() {
        AxisBuilder.Mode mode = AxisBuilder.Mode.TIME;

        axisBuilder.mode(mode);

        Map<String, Object> params = (Map<String, Object>) axisBuilder.build();
        assertEquals(mode.getValue(), params.get(PARAM_MODE));
    }

    @Test
    public void testTimeformat() {
        String timeformat = "%d%m%y";

        axisBuilder.timeformat(timeformat);

        Map<String, Object> params = (Map<String, Object>) axisBuilder.build();
        assertEquals(timeformat, params.get(PARAM_TIMEFORMAT).toString());
    }

    @Test
    public void testScaling() {
        AxisBuilder.Scaling scaling = AxisBuilder.Scaling.LOGARITHMIC;

        axisBuilder.scaling(scaling);

        Map<String, Object> params = (Map<String, Object>) axisBuilder.build();
        assertEquals(scaling.getValue(), params.get(PARAM_SCALING));
    }
}
