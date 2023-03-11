package chatjava;

import java.util.*;

import chatjava.rmi.*;
import chatjava.logging.*;
import chatjava.server.*;

public class ChatServerApp {

    RmiRegistry registry;

    static Io io;

    private static final String DEFAULT_SERVER_NAAM = "ChatJava One";

    private ServerProperties serverProperties;

    public static Logger logger = new ServerLogger();

    public static void main(String args[]) {        
        logger.info("ChatJava server gestart. Voer een naam in voor de server (alleen <Enter> voor default '" + DEFAULT_SERVER_NAAM + "').");

        var serverProperties = new ServerProperties();
        
        var chatGptApiKey = serverProperties.chatGptApiKey();
        logger.secret("Chat GPT API key: " + chatGptApiKey);

        // Inlezen naam die gebruiker ingeeft.
        io = new Io();
        var serverNaam = io.vraagInput();
        logger.info("Ok, ik heet '" + serverNaam + "'.\n RPC channel staat open en ik wacht op clients die me TCP'en 😀.");
        var registry = new RmiRegistry(serverNaam, logger);
    }

}