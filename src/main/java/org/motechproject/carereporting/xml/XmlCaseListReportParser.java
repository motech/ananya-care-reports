package org.motechproject.carereporting.xml;

import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.xml.mapping.reports.CaseListReport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

@Component
public class XmlCaseListReportParser {

    public CaseListReport parse(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CaseListReport.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(getSchema());
        return (CaseListReport) unmarshaller.unmarshal(file);
    }

    private Schema getSchema() {
        try {
            Resource schemaFile = new ClassPathResource("caseListReport.xsd");
            return SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(schemaFile.getFile());
        } catch (IOException | SAXException e) {
            throw new CareRuntimeException("Cannot open case list report schema file.", e);
        }
    }

}
