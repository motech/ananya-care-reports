package org.motechproject.carereporting.performance.helpers;

import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.xml.XmlIndicatorParser;
import org.motechproject.carereporting.xml.mapping.indicators.Indicator;

import java.util.Random;

public class XmlIndicatorTestParser extends XmlIndicatorParser {

    protected IndicatorEntity createIndicatorEntityFromXmlIndicator(Indicator indicator) {
        Random rand = new Random();
        indicator.setName("test-indicator-" + rand.nextInt() +indicator.getName());
        return super.createIndicatorEntityFromXmlIndicator(indicator);
    }

}
