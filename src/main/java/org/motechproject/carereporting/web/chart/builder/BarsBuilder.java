package org.motechproject.carereporting.web.chart.builder;

public class BarsBuilder extends ParamsBuilder {

    private static  final String PARAM_SHOW = "show";
    private static  final String PARAM_HORIZONTAL = "horizontal";
    private static  final String PARAM_SHADOW_SIZE = "shadowSize";
    private static  final String PARAM_BAR_WIDTH = "barWidth";

    public BarsBuilder show(boolean show) {
        return (BarsBuilder) param(PARAM_SHOW, show);
    }

    public BarsBuilder horizontal(boolean horizontal) {
        return (BarsBuilder) param(PARAM_HORIZONTAL, horizontal);
    }

    public BarsBuilder shadowSize(int shadowSize) {
        return (BarsBuilder) param(PARAM_SHADOW_SIZE, shadowSize);
    }

    public BarsBuilder barWidth(double barWidth) {
        return (BarsBuilder) param(PARAM_BAR_WIDTH, barWidth);
    }

}
