package chatjava.client;

import chatjava.logging.*;

public class ClientLogger extends Logger {

    public ClientLogger(String logLevelString) {
        if (logLevelString==null) {
            logLevelString = "";
        }
        logLevel = determineLogLevel(logLevelString);
    }

}