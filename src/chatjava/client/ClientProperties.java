package chatjava.client;

import chatjava.*;
import java.util.*;
import java.io.*;

public class ClientProperties extends GeneralProperties {

    public ClientProperties() {
        super("src/resources/client.properties");
    }

    public String defaultHost() {
        return getValueForKey("defaultHost");
    }

    public boolean askHostName() {
        return getBooleanValueForKey("askHostName", false);
    }

}