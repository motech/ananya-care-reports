package org.motechproject.carereporting.utils.configuration;

import org.apache.commons.lang.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigurationLocator {

    private static final String USER_HOME = SystemUtils.getUserHome().getAbsolutePath();

    private static final String CARE_CONF_DIRECTORY_NAME = ".care";

    private static final String CARE_LANGUAGES_DIRECTORY_NAME = "messages";

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

    public static Properties getCommCareConfiguration() throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = getCommCareConfigurationInputStream(loader);
        properties.load(stream);
        return properties;
    }

    private static InputStream getCommCareConfigurationInputStream(ClassLoader loader) {
        File customProperties = new File(getCommCareCustomPropertiesFilePath());
        if (customProperties.exists()) {
            return loader.getResourceAsStream(getCommCareCustomPropertiesFilePath());
        } else {
            return loader.getResourceAsStream(getCommCareDefaultPropertiesFileName());
        }
    }

    private static String getCommCareCustomPropertiesFilePath() {
        return getCareConfigurationDirectory() + File.separator + COMMCARE_CUSTOM_PROPERTIES_FILE_NAME;
    }

    private static String getCommCareDefaultPropertiesFileName() {
        return COMMCARE_DEFAULT_PROPERTIES_FILE_NAME;
    }
}
