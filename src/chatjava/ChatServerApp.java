package chatjava;

import java.util.*;

import chatjava.rmi.*;

public class ChatServerApp {

    RmiRegistry registry;

    static Io io;

    private static final String DEFAULT_SERVER_NAAM = "ChatJava One";

    public static void main(String args[]) {
        Logger.info("ChatJava server gestart. Voer een naam in voor de server (alleen <Enter> voor default '" + DEFAULT_SERVER_NAAM + "').");

        // Inlezen naam die gebruiker ingeeft.
        io = new Io();
        var serverNaam = io.vraagInput();
        Logger.info("Ok, ik heet '" + serverNaam + "'.\n RPC channel staat open en ik wacht op clients die me TCP'en 😀.");
        var registry = new RmiRegistry(serverNaam);
    }

}