package chatjava;

import java.util.*;

import chatjava.rmi.*;
import chatjava.*;

import chatjava.client.*;

public class ChatClientApp {

    HalloRmiClient helloClient;

    static Io io;

    // Voorlopig is logLevel op client standaard info.
    // TODO: Evt. ook een `client.properties` toevoegen met instelbaar logLevel erin zoals voor server app.
    private static ClientLogger logger = new ClientLogger(null);

    public static void main(String[] args) {
        // Default host voor RMI Registry, als leeg dan localhost op port 1099.
        var host = (args.length < 1) ? null : args[0];

        var client = new HalloRmiClient(host);

        var halloWereldResponse = client.zegHallo();
        logger.info("ChatServer draait! Response: " + halloWereldResponse);


        io = new Io(logger);
        var prompt = "Geef een aanmeldnaam om je mee aan te melden bij chatserver.";
        var aanmeldNaam = io.vraagInput(false, prompt);

        var response = client.meldAan(aanmeldNaam);
        logger.info(response);

        var bericht = "";        
        while (bericht!="stop") {
            bericht = io.vraagInput(false, "Tik een chat bericht en <enter> om te verzenden ('stop' om te stoppen).");

            // TODO Verstuur bericht en handel response/callback af.
            client.chat(bericht);
        }
    }
}