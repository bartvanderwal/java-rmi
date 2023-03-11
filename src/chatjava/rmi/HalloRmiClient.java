package chatjava.rmi;

import java.rmi.*;
import java.rmi.registry.*;

public class HalloRmiClient {

    private String host;

    public HalloRmiClient(String host) {
        this.host = host;
        proxy = lookupHalloProxy();
    }

    private HalloRmiInterface proxy;
    
    private HalloRmiInterface lookupHalloProxy() {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HalloRmiInterface proxy = (HalloRmiInterface) registry.lookup(HalloRmiInterface.NAAM);
            return proxy;
       } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println(HalloRmiInterface.NAAM + "niet bekend in RMI Registry: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    // We gaan ervan uit dat als ophalen proxy goed ging, er dan geen verdere RemoteExceptions meer optreden.
    // En gooien daarom RemoteException door als RunTimeException.
    public String zegHallo() {
        try {
            return proxy.zegHallo();
        } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
