package chatjava;

import java.util.*;

import chatjava.rmi.*;
import chatjava.logging.*;
import chatjava.server.*;

public class ChatServerApp {

    RmiRegistry registry;

    static Io io;

    private static final String DEFAULT_SERVER_NAAM = "ChatJava One";

    static private ServerProperties serverProperties;

    public static Logger logger = new ServerLogger();

    public static void main(String args[]) {        
        serverProperties = new ServerProperties();        
        var chatGptApiKey = serverProperties.chatGptApiKey();
        logger.secret("Chat GPT API key: " + chatGptApiKey);

        logger.info("ChatJava server gestart.");
        var prompt = "Voer een naam in voor de server (alleen <Enter> voor default '" + DEFAULT_SERVER_NAAM + "').";

        // Inlezen naam die gebruiker ingeeft.
        io = new Io(logger);
        var serverNaam = io.vraagInput(DEFAULT_SERVER_NAAM, prompt);
        logger.info("Ok, ik heet '" + serverNaam + "'.\n RPC channel staat open en ik wacht op clients die me TCP'en 😀.");
        var registry = new RmiRegistry(serverNaam, logger);
    }

}