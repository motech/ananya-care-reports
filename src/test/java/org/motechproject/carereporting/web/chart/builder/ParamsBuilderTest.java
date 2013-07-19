package org.motechproject.carereporting.web.chart.builder;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = "classpath:testContext.xml")
public class ParamsBuilderTest {

    @Test
    public void testAddingParamWithObject() {
        String key = "key1";
        Integer value = 123;
        ParamsBuilder builder = new ParamsBuilder();

        builder.param(key, value);

        Map<String, Object> params = (Map<String, Object>) builder.build();
        assertNotNull(params);
        assertEquals(1, params.size());
        assertEquals(value, params.get(key));
    }

    @Test
    public void testAddingParamWithParamsBuilder() {
        String helperKey = "key1";
        Integer helperValue = 123;
        String testedKey = "key2";
        ParamsBuilder helper = new ParamsBuilder();
        helper.param(helperKey, helperValue);
        ParamsBuilder tested = new ParamsBuilder();

        tested.param(testedKey, helper);

        Map<String, Object> params = (Map<String, Object>) tested.build();
        assertNotNull(params);
        assertEquals(1, params.size());
        assertEquals(helper.build(), params.get(testedKey));
    }
}
