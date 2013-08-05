package org.motechproject.carereporting.xml;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.dto.IndicatorDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class XmlIndicatorParser {

    @Transactional
    public IndicatorDto parse(InputStream is) throws DocumentException, SAXException, IOException {
        Element indicator = new SAXReader().read(is).getRootElement();
        String name = getElementValue(indicator, "name");
        String frequency = getElementValue(indicator, "frequency");
        String type = getElementValue(indicator, "type");
        //String formName = getElementValue(indicator, "form");
        String areaId = getElementValue(indicator, "areaId");
        String computedFieldId = getElementValue(indicator, "computedFieldId");

        //List<DefaultAttribute> categoryIds = indicator.selectNodes("categories/category/@id");
        List<Node> conditions = indicator.selectNodes("conditions/condition");
        for (Node condition: conditions) {
       ///     String fieldName = getElementValue(condition, "fieldName");
         //   String symbol = getElementValue(condition, "symbol");
        }

        IndicatorDto indicatorDto = new IndicatorDto(getIndicatorTypeIdFromName(type), new HashSet<Integer>(), Integer.valueOf(areaId),
                new HashSet<Integer>(), Integer.valueOf(computedFieldId), null, new HashSet<Integer>(), new HashSet<ReportEntity>(), getFrequencyFromString(frequency),
                name, BigDecimal.ONE);
        return indicatorDto;
    }

    private String getElementValue(Node node, String propertyName) {
        return node.selectSingleNode(propertyName).getStringValue();
    }

    private Integer getIndicatorTypeIdFromName(String typeName) {
        switch (typeName.toLowerCase()) {
            case "average":
                return 1;
            case "count":
                return 2;
            case "percentage":
                return 3;
            case "sum":
                return 4;
        }
        throw new IllegalArgumentException("Indicator type: " + typeName + " is invalid.");
    }

    private Integer getFrequencyFromString(String frequency) {
        switch (frequency) {
            case "monthly":
                return 30;
            case "weekly":
                return 7;
        }
        throw new IllegalArgumentException("Frequency " + frequency + " not supported.");
    }
}
