package org.motechproject.carereporting.web.chart.builder;

public class LegendBuilder extends ParamsBuilder {

    private static final String PARAM_POSITION = "position";
    private static final String PARAM_BACKGROUND_COLOR = "backgroundColor";

    public LegendBuilder position(String position) {
        return (LegendBuilder) param(PARAM_POSITION, position);
    }

    public LegendBuilder backgroundColor(String backgroundColor) {
        return (LegendBuilder) param(PARAM_BACKGROUND_COLOR, backgroundColor);
    }

}
