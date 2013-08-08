package org.motechproject.carereporting.export.csv;

import liquibase.util.csv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CsvExportHelper {

    public void saveCsvFile(List<String[]> data, String filePath) throws IOException {
        createDirectories(filePath);
        CSVWriter writer = new CSVWriter(new FileWriter(filePath), '\t');
        try {
            writer.writeAll(data);
            writer.flush();
        } finally {
            writer.close();
        }
    }

    private void createDirectories(String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();

        if(!parent.exists()) {
            parent.mkdirs();
        }
    }

}
