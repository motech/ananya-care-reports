package org.motechproject.carereporting.export.csv;

import liquibase.util.csv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CsvExportHelper {

    public void saveCsvFile(List<String[]> data, String filePath) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(filePath), '\t');
        try {
            writer.writeAll(data);
            writer.flush();
        } finally {
            writer.close();
        }
    }

}
