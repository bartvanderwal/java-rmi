package chatjava.server;

import chatjava.*;
import java.util.*;
import java.io.*;

public class ServerProperties {

    public static final String PROPERTIES_FILE_PATH = "src/resources/server.properties";

    private Properties properties;

    public ServerProperties() {
        properties = new Properties();

        try (InputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            // load a properties file
            properties.load(input);    
        } catch (IOException ex) {
            throw new ChatJavaException("Het bestand " + PROPERTIES_FILE_PATH + "lijkt niet te bestaan.", ex);
        }
    }

    public String chatGptApiKey() {
        // Bron: https://stackoverflow.com/questions/8285595/reading-properties-file-in-java        
        return properties.getProperty("chatGptApiToken");
    }
}