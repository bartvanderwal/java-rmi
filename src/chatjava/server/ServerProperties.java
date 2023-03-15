package chatjava.server;

import chatjava.*;
import java.util.*;
import java.io.*;

public class ServerProperties extends GeneralProperties {

    protected static final String PROPERTIES_FILE_PATH = "src/resources/server.properties";

    public ServerProperties() {
        super(PROPERTIES_FILE_PATH);
    }
    public String chatGptApiKey() {
        // Bron: https://stackoverflow.com/questions/8285595/reading-properties-file-in-java        
        return getValueForKey("chatGptApiToken");
    }

    public boolean askServerName() {
        return getBooleanValueForKey("askServerName", false);
    }

}