package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.IndicatorValueEntity;

import java.io.IOException;
import java.util.List;

public interface ExportService {

    byte[] convertIndicatorValuesToBytes(List<IndicatorValueEntity> indicatorValues) throws IOException;

}
