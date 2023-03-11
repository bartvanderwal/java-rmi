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
        HalloRmiInterface skeleton = null;
        try {
            registry = LocateRegistry.getRegistry(1099);
        } catch (RemoteException e) {
            logger.error("RMI register bestaat nog niet. Dan aanmaken. Zie foutmelding:" + e.getMessage());
            try {
                registry = LocateRegistry.createRegistry(1099);
            } catch (RemoteException e2) {
                logger.error("RMI register aanmaken lukte niet, ondanks dat deze niet bestond. Zie foutmelding:" + e2.getMessage());
            }
        } catch (Exception e) {
            throw new ChatJavaException("Server exception.", e);
        }
        // Bind the remote object's stub in the registry.
        var chatServer = new HalloRmiServer(serverNaam);
        try {
            skeleton = (HalloRmiInterface) UnicastRemoteObject.exportObject(chatServer, 0);
        } catch (RemoteException e2) {
            logger.error("Skeleton ophalen uit RMI server lukt niet. Zie foutmelding:" + e2.getMessage());
        }
        try {
            if (registry.lookup(HalloRmiInterface.NAAM)==null) {
                registry.bind(HalloRmiInterface.NAAM, skeleton);
            }
        } catch (AlreadyBoundException e3) {
            logger.error("Interface " + HalloRmiInterface.NAAM + " al gebonden.");
        } catch (NotBoundException e3) {
            logger.error("Interface " + HalloRmiInterface.NAAM + " binden mislukt.");
        } catch (RemoteException e3) {
            logger.error("Skeleton binden lukt niet. Zie foutmelding:" + e3.getMessage());
        }
        logger.info("Server staat klaar.");
    }
}