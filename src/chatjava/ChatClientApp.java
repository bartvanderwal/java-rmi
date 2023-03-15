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
        var prompt = "Voer de host naam (alleen <Enter> voor default '" + host + "' uit properties of command line).";

        // Inlezen naam die gebruiker ingeeft.
        var logger = new ClientLogger();

        Io io = new Io(logger);
        host = io.vraagInput(host, prompt);

        logger.info("Ok, host: '" + host + "'.");
        try {
            var client = new HalloRmiClient(host);
            client.startDialoog();
        } catch (RemoteException e) {
            throw new ChatJavaException("Client aanmaak error. Message: " + e.getMessage(), e);
        }
    }
}