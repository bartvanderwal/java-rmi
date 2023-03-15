package chatjava;

import java.rmi.RemoteException;
import java.util.*;

import chatjava.rmi.*;
import chatjava.client.*;

public class ChatClientApp {


    public static void main(String[] args) {
        // Default host voor RMI Registry, als leeg dan localhost op port 1099.
        var clientProperties = new ClientProperties();
        var hostFromProperties = clientProperties.defaultHost();

        var hostFromCommandLine = (args.length < 1) ? null : args[0];
        var host = (hostFromCommandLine!=null) ? hostFromCommandLine : hostFromProperties;

        // Inlezen naam die gebruiker ingeeft.
        var logger = new ClientLogger();
        if (clientProperties.askHostName()) {
            var prompt = "Voer de host naam in waar chat server draait (alleen <Enter> voor default '" + host + "' uit properties of command line of gebruik 'localhost' als je zelf chat server runt).";
            Io io = new Io(logger);
            host = io.vraagInput(host, prompt);
        }
        logger.info("Chat Client: Ik gebruik als host (domeinnaam) voor de chat server: '" + host + "'.");
        ChatJavaRmiClient client = null;
        try {
            client = new ChatJavaRmiClient(host);
        } catch (RemoteException e) {
            throw new ChatJavaException("Client aanmaak error. Message: " + e.getMessage(), e);
        }
        try {
            client.startChatten();
        } catch (RemoteException e) {
            throw new ChatJavaException("Probleem tijdens chatten. Message: " + e.getMessage(), e);
        }
    }
}