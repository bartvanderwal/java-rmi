package chatjava;

import java.util.*;

import chatjava.rmi.*;

public class ChatServerApp {

    RmiRegistry registry;

    private static final String DEFAULT_SERVER_NAAM = "ChatJava One";

    public static void main(String args[]) {

        Logger.info("ChatJava server gestart. Voer een naam in voor de server (alleen <Enter> voor default '" + DEFAULT_SERVER_NAAM + "').");

        // Inlezen naam die gebruiker ingeeft.
        // Bron: https://stackoverflow.com/questions/8560395/how-to-use-readline-method-in-java.
        var scanner = new Scanner(System.in);
        var serverNaam = scanner.next();
        Logger.info("Ok, ik heet '" + serverNaam + "'.\n RPC channel staat open en ik wacht op clients die me TCP'en 😀.");
        var registry = new RmiRegistry(serverNaam);
    }

}