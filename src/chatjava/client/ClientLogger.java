package chatjava.client;

import chatjava.logging.*;

public class ClientLogger extends Logger {

    public ClientLogger() {
        properties = new ClientProperties();
        var logLevelString = properties.logLevel();
        logLevel = determineLogLevel(logLevelString);
        info("Client logger aangemaakt. LogLevel is: " + logLevel);
    }

    public ClientLogger(String logLevelString) {
        if (logLevelString==null) {
            logLevelString = "";
        }
        logLevel = determineLogLevel(logLevelString);
    }

}