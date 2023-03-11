package chatjava.rmi;

import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import chatjava.*;
import chatjava.logging.*;

public class RmiRegistry {
    
    private Logger logger;

    public RmiRegistry(String serverNaam, Logger logger) {
        this.logger = logger;
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            // Bind the remote object's stub in the registry.
            var chatServer = new HalloRmiServer(serverNaam);
            var skeleton = (HalloRmiInterface) UnicastRemoteObject.exportObject(chatServer, 0);

            try {
                registry.bind(HalloRmiInterface.NAAM, skeleton);
            } catch (AlreadyBoundException e) {
                logger.error("Interface " + HalloRmiInterface.NAAM + " al gebonden.");
            }
            logger.info("Server staat klaar.");
        } catch (RemoteException e) {
            throw new ChatJavaException("Probleem bij aanmaken RMI registry. Runt wellicht al.", e);
        } catch (Exception e) {
            throw new ChatJavaException("Server exception.", e);
        }
    }
}