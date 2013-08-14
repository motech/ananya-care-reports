package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.IndicatorValueEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExportService {

    byte[] convertIndicatorValuesToBytes(List<IndicatorValueEntity> indicatorValues) throws IOException;

    byte[] convertRowMapToBytes(List<Map<String, Object>> rowMap);

}
