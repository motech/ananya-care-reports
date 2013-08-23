package org.motechproject.carereporting.web.chart.builder;

public class AxisBuilder extends ParamsBuilder {

    public enum Mode {
        TIME("time");

        private String value;

        Mode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public enum Scaling {
        LINEAR("linear"), LOGARITHMIC("logarithmic");

        private String value;

        Scaling(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final String PARAM_TITLE = "title";
    private static final String PARAM_SHOW_LABELS = "showLabels";
    private static final String PARAM_MINOR_TICK_FREQ = "minorTickFreq";
    private static final String PARAM_MIN = "min";
    private static final String PARAM_AUTO_SCALE_MARGIN = "autoScaleMargin";
    private static final String PARAM_MODE = "mode";
    private static final String PARAM_TIMEFORMAT = "timeformat";
    private static final String PARAM_SCALING = "scaling";
    private static final String PARAM_MAX = "max";

    public AxisBuilder max(double max) {
        return (AxisBuilder) param(PARAM_MAX, max);
    }

    public AxisBuilder title(String label) {
        return (AxisBuilder) param(PARAM_TITLE, label);
    }

    public AxisBuilder showLabels(boolean showLabels) {
        return (AxisBuilder) param(PARAM_SHOW_LABELS, showLabels);
    }

    public AxisBuilder minorTickFreq(int minorTickFreq) {
        return (AxisBuilder) param(PARAM_MINOR_TICK_FREQ, minorTickFreq);
    }

    public AxisBuilder min(double min) {
        return (AxisBuilder) param(PARAM_MIN, min);
    }

    public AxisBuilder autoScaleMargin(int autoScaleMargin) {
        return (AxisBuilder) param(PARAM_AUTO_SCALE_MARGIN, autoScaleMargin);
    }

    public AxisBuilder mode(Mode mode) {
        return (AxisBuilder) param(PARAM_MODE, mode.getValue());
    }

    public AxisBuilder timeformat(String timeformat) {
        return (AxisBuilder) param(PARAM_TIMEFORMAT, timeformat);
    }

    public AxisBuilder scaling(Scaling scaling) {
        return (AxisBuilder) param(PARAM_SCALING, scaling.getValue());
    }

}