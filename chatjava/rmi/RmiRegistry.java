package chatjava.rmi;

import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RmiRegistry {
    
    public RmiRegistry() {
        Registry registry = LocateRegistry.createRegistry(1099);
        try {

            // Bind the remote object's stub in the registry.
            HelloRmiServer helloServer = new HelloRmiServer();
            HelloRmiInterface skeleton = (HelloRmiInterface) UnicastRemoteObject.exportObject(helloServer, 0);

            try {
                registry.bind(HelloRmiInterface.NAAM, skeleton);
            } catch (AlreadyBoundException e) {
                System.err.println("Interface " + HelloRmiInterface.NAAM + " already bound");
            }
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}