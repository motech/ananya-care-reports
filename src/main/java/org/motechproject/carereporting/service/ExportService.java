package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.IndicatorValueEntity;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ExportService {

    byte[] convertIndicatorValuesToBytes(List<IndicatorValueEntity> indicatorValues) throws IOException, ParseException;

    byte[] convertRowMapToBytes(List<String> headers, List<Map<String, Object>> rowMap);

}
