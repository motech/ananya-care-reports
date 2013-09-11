package org.motechproject.carereporting.web.chart.builder;

public class LegendBuilder extends ParamsBuilder {

    public enum Position {
        BOTTOM_LEFT("sw"),
        BOTTOM_RIGHT("se");

        private String value;

        Position(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final String PARAM_POSITION = "position";
    private static final String PARAM_BACKGROUND_COLOR = "backgroundColor";
    private static final String PARAM_SHOW = "show";
    private static final String PARAM_LABEL_BOX_BORDER_COLOR = "labelBoxBorderColor";
    private static final String PARAM_LABEL_BOX_MARGIN = "labelBoxMargin";
    private static final String PARAM_MARGIN = "margin";
    private static final String PARAM_BACKGROUND_OPACITY = "backgroundOpacity";

    public LegendBuilder position(Position position) {
        return (LegendBuilder) param(PARAM_POSITION, position.getValue());
    }

    public LegendBuilder backgroundColor(String backgroundColor) {
        return (LegendBuilder) param(PARAM_BACKGROUND_COLOR, backgroundColor);
    }

    public LegendBuilder show(boolean show) {
        return (LegendBuilder) param(PARAM_SHOW, show);
    }

    public LegendBuilder labelBoxBorderColor(String color) {
        return (LegendBuilder) param(PARAM_LABEL_BOX_BORDER_COLOR, color);
    }

    public LegendBuilder labelBoxMargin(Integer margin) {
        return (LegendBuilder) param(PARAM_LABEL_BOX_MARGIN, margin);
    }

    public LegendBuilder margin(Integer margin) {
        return (LegendBuilder) param(PARAM_MARGIN, margin);
    }

    public LegendBuilder backgroundOpacity(Integer opacity) {
        return (LegendBuilder) param(PARAM_BACKGROUND_OPACITY, opacity);
    }

}
