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
        try {
            registry = LocateRegistry.createRegistry(1099);
            // Bind the remote object's stub in the registry.
            var chatServer = new HalloRmiServer(serverNaam);
            HalloRmiInterface skeleton = (HalloRmiInterface) UnicastRemoteObject.exportObject(chatServer, 0);
            registry.bind(HalloRmiInterface.NAAM, skeleton);
        } catch (Exception e) {
            throw new ChatJavaException(e);
        }
        logger.info("Chat Server '" + serverNaam + "' staat klaar 😀.");
    }
}