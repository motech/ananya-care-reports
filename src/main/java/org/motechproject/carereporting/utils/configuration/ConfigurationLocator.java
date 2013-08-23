package org.motechproject.carereporting.utils.configuration;

import org.apache.commons.lang.SystemUtils;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigurationLocator {

    private static final String USER_HOME = SystemUtils.getUserHome().getAbsolutePath();

    private static final String CARE_CONF_DIRECTORY_NAME = ".care";

    private static final String CARE_LANGUAGES_DIRECTORY_NAME = "messages";

    private static final String CARE_XML_DIRECTORY_NAME = "xml";

    private static final String COMMCARE_DEFAULT_PROPERTIES_FILE_NAME = "commcare.default.properties";

    private static final String COMMCARE_CUSTOM_PROPERTIES_FILE_NAME = "commcare.custom.properties";

    private ConfigurationLocator() {

    }

    public static String getCareConfigurationDirectory() {
        return USER_HOME + File.separator + CARE_CONF_DIRECTORY_NAME;
    }

    public static String getCareLanguagesDirectory() {
        return getCareConfigurationDirectory() + File.separator + CARE_LANGUAGES_DIRECTORY_NAME;
    }

    public static String getCareXmlDirectory() {
        return getCareConfigurationDirectory() + File.separator + CARE_XML_DIRECTORY_NAME;
    }

    public static Properties getCommCareConfiguration() throws IOException {
        Properties customProperties = getCommCareCustomConfiguration();
        Properties defaultProperties = getCommCareDefaultConfiguration();
        if (customProperties.size() == 0) {
            return getCommCareDefaultConfiguration();
        } else {
            completeCustomProperties(customProperties, defaultProperties);
            return customProperties;
        }
    }

    private static Properties getCommCareCustomConfiguration() throws IOException {
        Properties properties = new Properties();
        File customPropertiesFile = new File(getCommCareCustomPropertiesFilePath());
        if (customPropertiesFile.exists()) {
            InputStream stream = new FileSystemResource(customPropertiesFile).getInputStream();
            properties.load(stream);
        }
        return properties;
    }

    private static Properties getCommCareDefaultConfiguration() throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream defaultStream = loader.getResourceAsStream(getCommCareDefaultPropertiesFileName());
        properties.load(defaultStream);
        return properties;
    }

    private static void completeCustomProperties(Properties customProperties, Properties defaultProperties) {
        for (Object propertyKey : defaultProperties.keySet()) {
            if (!customProperties.containsKey(propertyKey)) {
                customProperties.setProperty(propertyKey.toString(), defaultProperties.getProperty(propertyKey.toString()));
            }
        }
    }

    private static String getCommCareCustomPropertiesFilePath() {
        return getCareConfigurationDirectory() + File.separator + COMMCARE_CUSTOM_PROPERTIES_FILE_NAME;
    }

    private static String getCommCareDefaultPropertiesFileName() {
        return COMMCARE_DEFAULT_PROPERTIES_FILE_NAME;
    }
}
