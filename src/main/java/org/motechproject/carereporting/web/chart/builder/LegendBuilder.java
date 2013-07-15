package org.motechproject.carereporting.web.chart.builder;

public class LegendBuilder extends ParamsBuilder {

    public enum Position {
        BOTTOM_LEFT("se");

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

    public LegendBuilder position(Position position) {
        return (LegendBuilder) param(PARAM_POSITION, position.getValue());
    }

    public LegendBuilder backgroundColor(String backgroundColor) {
        return (LegendBuilder) param(PARAM_BACKGROUND_COLOR, backgroundColor);
    }

}
