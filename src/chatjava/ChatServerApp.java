package chatjava;

import java.util.*;

import chatjava.rmi.*;
// import chatjava.logging.*;
import chatjava.server.*;

public class ChatServerApp {

    static RmiRegistry registry;

    static Io io;

    private static final String DEFAULT_SERVER_NAAM = "ChatJava One";

    static private ServerProperties serverProperties;

    public static ServerLogger logger = new ServerLogger();

    public static void main(String args[]) {        
        serverProperties = new ServerProperties();        
        var chatGptApiKey = serverProperties.chatGptApiKey();
        logger.secret("Chat GPT API key: " + chatGptApiKey);

        logger.info("ChatJava server gestart.");
        var serverNaam = DEFAULT_SERVER_NAAM;
        if (serverProperties.askServerName()) {
            var prompt = "Voer een naam in voor de server (alleen <Enter> voor default '" + DEFAULT_SERVER_NAAM + "').";
            io = new Io(logger);
            serverNaam = io.vraagInput(DEFAULT_SERVER_NAAM, prompt);
        }
        // Inlezen naam die gebruiker ingeeft.
        registry = new RmiRegistry(serverNaam, logger);
    }

}