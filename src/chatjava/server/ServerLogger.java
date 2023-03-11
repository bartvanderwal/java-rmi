package chatjava.server;

import chatjava.logging.*;

public class ServerLogger extends Logger {

    private ServerProperties serverProperties;

    public ServerLogger() {
        serverProperties = new ServerProperties();
        var logLevelString = serverProperties.logLevel();
        logLevel = determineLogLevel(logLevelString);
        info("Server logger aangemaakt. LogLevel is: " + logLevel);
    }

}