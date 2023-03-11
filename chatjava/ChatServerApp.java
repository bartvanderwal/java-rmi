package chatjava;

import java.util.*;

import chatjava.rmi.*;

public class ChatServerApp {

    RmiRegistry registry;

    private static final String DEFAULT_SERVER_NAAM = "ChatJava One";

    public static void main(String args[]) {

        print("ChatJava server gestart. Voer een naam in voor de server (alleen <Enter> voor default '" + DEFAULT_SERVER_NAAM + "').");

        // Inlezen naam die gebruiker ingeeft.
        // Bron: https://stackoverflow.com/questions/8560395/how-to-use-readline-method-in-java.
        var scanner = new Scanner(System.in);
        var serverNaam = scanner.next();
        print("Ok, Ik heet '" + serverNaam + "'m, RPC channel geopend en ik wacht op clients die TCP'en 😀.");
        RmiRegistry registry = new RmiRegistry(serverNaam);
    }

    private static void print(String tekst) {
        System.out.println(tekst);
    }
}