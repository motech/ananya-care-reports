package org.motechproject.carereporting.web.chart.builder;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ChartBuilderTest {

    private static final String PARAM_TITLE = "title";
    private static final String PARAM_HTML_TEXT = "HtmlText";

    private ChartBuilder chartBuilder = new ChartBuilder();

    @Test
    public void testTitle() {
        String title = "title1";

        chartBuilder.title(title);

        Map<String, Object> params = chartBuilder.build().getSettings();
        assertEquals(title, params.get(PARAM_TITLE));
    }

    @Test
    public void testHtmlText() {
        boolean html = true;

        chartBuilder.htmlText(html);

        Map<String, Object> params = chartBuilder.build().getSettings();
        assertEquals(html, params.get(PARAM_HTML_TEXT));
    }
}
