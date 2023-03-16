package chatjava;

import java.util.*;
import java.io.*;

public abstract class GeneralProperties {

    protected Properties properties;

    public GeneralProperties(String propertiesFilePath) {
        properties = new Properties();

        try {
            InputStream input = new FileInputStream(propertiesFilePath);
            // load a properties file.
            properties.load(input);    
        } catch (IOException ex) {
            throw new ChatJavaException("Het bestand '" + propertiesFilePath + "' lijkt niet te bestaan.", ex);
        }
    }

    public String logLevel() {
        return getValueForKey("logLevel");
    }

    protected String getValueForKey(String key) {
        return properties.getProperty(key);
    }

    protected boolean getBooleanValueForKey(String key, boolean defaultValue) {
        var result = properties.getProperty(key);
        return Boolean.parseBoolean(result);
    }

}