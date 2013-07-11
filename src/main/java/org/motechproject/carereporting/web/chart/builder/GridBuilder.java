package org.motechproject.carereporting.web.chart.builder;

public class GridBuilder extends ParamsBuilder {

    private static final String PARAM_VERTICAL_LINES = "verticalLines";
    private static final String PARAM_HORIZONTAL_LINES = "horizontalLines";
    private static final String PARAM_MINOR_VERTICAL_LINES = "minorVerticalLines";

    public GridBuilder verticalLines(boolean verticalLines) {
        return (GridBuilder) param(PARAM_VERTICAL_LINES, verticalLines);
    }

    public GridBuilder horizontalLines(boolean horizontalLines) {
        return (GridBuilder) param(PARAM_HORIZONTAL_LINES, horizontalLines);
    }

    public GridBuilder minorVerticalLines(boolean minorVerticalLines) {
        return (GridBuilder) param(PARAM_MINOR_VERTICAL_LINES, minorVerticalLines);
    }

}
