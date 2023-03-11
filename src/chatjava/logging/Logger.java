package chatjava.logging;

import chatjava.logging.*;
import chatjava.*;

public abstract class Logger {

    protected LogLevel logLevel;
    
    public void secret(String tekst) {
        if (logLevel==LogLevel.SECRET) {
            println(tekst);
        }
    }

    public void info(String tekst) {
        if (logLevel == LogLevel.INFO || logLevel == LogLevel.SECRET) {
            println(tekst);
        }
    }

    public void error(String tekst) {
        println(tekst);
    }

    private void println(String tekst) {
        System.out.println(tekst);
    }

    protected LogLevel determineLogLevel(String logLevelString) {
        switch (logLevelString) {
            case "error":
                return LogLevel.ERROR;
            case "":
            case "info":
                return LogLevel.INFO;
            case "secret":
                return LogLevel.SECRET;
            default: throw new ChatJavaException("Loglevel ongeldige waarde '" + logLevel + "', gebruik 'error', 'info' (default) of 'secret' (of laat weg of leeg).");
        }
    }
}