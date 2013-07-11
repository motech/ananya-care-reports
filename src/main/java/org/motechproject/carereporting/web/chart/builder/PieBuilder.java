package org.motechproject.carereporting.web.chart.builder;

public class PieBuilder extends ParamsBuilder {

    private static final String PARAM_SHOW = "show";
    private static final String PARAM_EXPLODE = "explode";

    public PieBuilder show(boolean show) {
        return (PieBuilder) param(PARAM_SHOW, show);
    }

    public PieBuilder explode(int explode) {
        return (PieBuilder) param(PARAM_EXPLODE, explode);
    }

}
