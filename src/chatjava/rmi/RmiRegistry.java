package chatjava.rmi;

import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import chatjava.*;
// import chatjava.Logger;

public class RmiRegistry {
    
    public RmiRegistry(String serverNaam) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            // Bind the remote object's stub in the registry.
            var chatServer = new HalloRmiServer(serverNaam);
            var skeleton = (HalloRmiInterface) UnicastRemoteObject.exportObject(chatServer, 0);

            try {
                registry.bind(HalloRmiInterface.NAAM, skeleton);
            } catch (AlreadyBoundException e) {
                Logger.error("Interface " + HalloRmiInterface.NAAM + " al gebonden.");
            }
            Logger.info("Server klaar");
        } catch (RemoteException e) {
            System.err.println("Probleem bij aanmaken RMI registry. Runt wellicht al. Error: " + e.toString());
            throw new ChatJavaException(e);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            throw new ChatJavaException(e);
        }
    }
}