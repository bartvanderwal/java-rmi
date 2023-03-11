package chatjava;

import java.util.*;

import chatjava.rmi.*;
import chatjava.*;

public class ChatClientApp {

    HalloRmiClient helloClient;

    static Io io;

    public static void main(String[] args) {
        // Default host voor RMI Registry, als leeg dan localhost op port 1099.
        var host = (args.length < 1) ? null : args[0];

        var client = new HalloRmiClient(host);

        var halloWereldResponse = client.zegHallo();
        Logger.info("ChatServer draait! Response: " + halloWereldResponse);

        Logger.info("Geef een aanmeldnaam om je mee aan te melden bij chatserver.");
        io = new Io();
        var aanmeldNaam = io.vraagInput();

        var response = client.meldAan(aanmeldNaam);
        Logger.info(response);
        // io = new Io();
        // var serverNaam = io.vraagInput();

    }
}