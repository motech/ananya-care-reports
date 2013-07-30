package org.motechproject.carereporting.web.chart.builder;

public class SelectionBuilder extends ParamsBuilder {

    public enum Mode {
        X("x");

        private String value;

        Mode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final String PARAM_MODE = "mode";
    private static final String PARAM_FPS = "fps";

    public SelectionBuilder mode(Mode mode) {
        return (SelectionBuilder) param(PARAM_MODE, mode.getValue());
    }

    public SelectionBuilder fps(int fps) {
        return (SelectionBuilder) param(PARAM_FPS, fps);
    }
}
