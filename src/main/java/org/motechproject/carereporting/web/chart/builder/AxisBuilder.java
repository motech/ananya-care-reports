package org.motechproject.carereporting.web.chart.builder;

public class AxisBuilder extends ParamsBuilder {

    private static final String PARAM_SHOW_LABELS = "showLabels";
    private static final String PARAM_MINOR_TICK_FREQ = "minorTickFreq";
    private static final String PARAM_MIN = "min";
    private static final String PARAM_AUTO_SCALE_MARGIN = "autoScaleMargin";

    public AxisBuilder showLabels(boolean showLabels) {
        return (AxisBuilder) param(PARAM_SHOW_LABELS, showLabels);
    }

    public AxisBuilder minorTickFreq(int minorTickFreq) {
        return (AxisBuilder) param(PARAM_MINOR_TICK_FREQ, minorTickFreq);
    }

    public AxisBuilder min(int min) {
        return (AxisBuilder) param(PARAM_MIN, min);
    }

    public AxisBuilder autoScaleMargin(int autoScaleMargin) {
        return (AxisBuilder) param(PARAM_AUTO_SCALE_MARGIN, autoScaleMargin);
    }
}

