package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.exception.CareNoValuesException;
import org.motechproject.carereporting.export.csv.CsvExportHelper;
import org.motechproject.carereporting.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CsvExportServiceImpl implements ExportService {

    @Autowired
    private CsvExportHelper csvExportHelper;

    @Value("${csv.export.path}")
    private String directory;

    @Override
    public void exportIndicatorValues(List<IndicatorValueEntity> indicatorValues, String filePath) throws IOException {
        List<String[]> lines = prepareCsvLines(indicatorValues);
        csvExportHelper.saveCsvFile(lines, filePath);
    }

    @Override
    public void exportIndicatorValues(List<IndicatorValueEntity> indicatorValues) throws IOException {
        if(indicatorValues.size() == 0) {
            throw new CareNoValuesException();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy;HH:mm");
        String path = directory + "/" + indicatorValues.get(0).getIndicator().getName() + "." + simpleDateFormat.format(new Date()) + ".csv";
        exportIndicatorValues(indicatorValues, path);
    }

    private List<String[]> prepareCsvLines(List<IndicatorValueEntity> indicatorValues) {
        List<String[]> csvLines = new ArrayList<String[]>();
        for (IndicatorValueEntity indicatorValue : indicatorValues) {
            List<String> line = new ArrayList<String>();
            line.add(indicatorValue.getArea().getName());
            line.add(indicatorValue.getFrequency().getFrequencyName());
            line.add(indicatorValue.getValue().toString());
            csvLines.add(line.toArray(new String[line.size()]));
        }
        return csvLines;
    }

}
