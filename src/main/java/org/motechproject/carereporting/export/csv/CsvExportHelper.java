package org.motechproject.carereporting.export.csv;

import liquibase.util.csv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

@Component
public class CsvExportHelper {

    public ByteArrayInputStream convertToCsvFile(List<String[]> data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        CSVWriter csvWriter = new CSVWriter(writer, ',');
        try {
            csvWriter.writeAll(data);
            csvWriter.flush();
        } finally {
            csvWriter.close();
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}
