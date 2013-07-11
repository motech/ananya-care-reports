package org.motechproject.carereporting.web.chart.builder;

import org.motechproject.carereporting.web.chart.Chart;
import org.motechproject.carereporting.web.chart.Serie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartBuilder extends ParamsBuilder {

    private static final String PARAM_TITLE = "title";
    private static final String PARAM_HTML_TEXT = "HtmlText";
    private static final String PARAM_X_AXIS = "xaxis";
    private static final String PARAM_Y_AXIS = "yaxis";
    private static final String PARAM_GRID = "grid";
    private static final String PARAM_LEGEND = "legend";
    private static final String PARAM_MOUSE = "mouse";
    private static final String PARAM_PIE = "pie";
    private static final String PARAM_BARS = "bars";

    private List<Serie> data = new ArrayList<>();

    public ChartBuilder() {
    }

    public ChartBuilder serie(SerieBuilder serieBuilder) {
        return serie(serieBuilder.build());
    }

    public ChartBuilder serie(Serie serie) {
        data.add(serie);
        return this;
    }

    public ChartBuilder title(String title) {
        return (ChartBuilder) param(PARAM_TITLE, title);
    }

    public ChartBuilder htmlText(boolean htmlText) {
        return (ChartBuilder) param(PARAM_HTML_TEXT, htmlText);
    }

    public ChartBuilder xAxis(ParamsBuilder builder) {
        return (ChartBuilder) param(PARAM_X_AXIS, builder.build());
    }

    public ChartBuilder yAxis(ParamsBuilder builder) {
        return (ChartBuilder) param(PARAM_Y_AXIS, builder.build());
    }

    public ChartBuilder grid(ParamsBuilder builder) {
        return (ChartBuilder) param(PARAM_GRID, builder.build());
    }

    public ChartBuilder legend(ParamsBuilder builder) {
        return (ChartBuilder) param(PARAM_LEGEND, builder.build());
    }

    public ChartBuilder mouse(ParamsBuilder builder) {
        return (ChartBuilder) param(PARAM_MOUSE, builder.build());
    }

    public ChartBuilder pie(ParamsBuilder builder) {
        return (ChartBuilder) param(PARAM_PIE, builder.build());
    }

    public ChartBuilder bars(ParamsBuilder builder) {
        return (ChartBuilder) param(PARAM_BARS, builder.build());
    }

    @Override
    public Chart build() {
        return new Chart(data, (Map<String, Object>) super.build());
    }
}
