package chatjava.rmi;

import java.rmi.*; // Alle excepties o.a.
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.net.BindException;

import chatjava.*;
import chatjava.logging.*;

public class RmiRegistry {
    
    private Logger logger;

    public RmiRegistry(String serverNaam, Logger logger) {
        this.logger = logger;
        Registry registry = null;
        logger.info("Aanmaken RMI Registry");
        try {
            registry = LocateRegistry.createRegistry(1099);
            // Bind the remote object's stub in the registry.
            logger.info("Chat Server '" + serverNaam + "' registreren bij registry.");
            
            // De UnicastRemoteObject.exportObject aanroepen met klasse HalloRmiServer is niet nodig als deze klasse al UnicastRemoteObject extend.
            var chatServer = new ChatJavaRmiServer(serverNaam);
            ChatJavaRmiInterface skeleton = (ChatJavaRmiInterface) UnicastRemoteObject.exportObject(chatServer, 0);
            registry.rebind(ChatJavaRmiInterface.NAAM, skeleton);
        } catch (Exception e) {
            throw new ChatJavaException(e);
        }
        logger.info("Chat Server '" + serverNaam + "' staat klaar 😀.");
    }
}