package org.motechproject.carereporting.web.chart.builder;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MouseBuilderTest {

    private static final String PARAM_TRACK = "track";
    private static final String PARAM_RELATIVE = "relative";

    private MouseBuilder mouseBuilder = new MouseBuilder();

    @Test
    public void testTrack() {
        boolean track = false;

        mouseBuilder.track(track);

        Map<String, Object> params = (Map<String, Object>) mouseBuilder.build();
        assertEquals(track, params.get(PARAM_TRACK));
    }

    @Test
    public void testRelative() {
        boolean relative = true;

        mouseBuilder.relative(relative);

        Map<String, Object> params = (Map<String, Object>) mouseBuilder.build();
        assertEquals(relative, params.get(PARAM_RELATIVE));
    }
}
