package chatjava.rmi;

import java.rmi.*;
import java.rmi.registry.*;

import chatjava.ChatJavaException;

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
            throw new ChatJavaException("Remote exception op Chat Client", e);
        } catch (NotBoundException e) {
            throw new ChatJavaException("Client: " + HalloRmiInterface.NAAM + " interface niet bekend in RMI Registry.", e);
        }
    }

    // We gaan ervan uit dat als ophalen proxy goed ging, er dan geen verdere RemoteExceptions meer optreden.
    // En gooien daarom RemoteException door als RunTimeException.
    public String zegHallo() {
        try {
            return proxy.zegHallo();
        } catch (RemoteException e) {
            throw new ChatJavaException("Client exception: " + e.toString(), e);
        }
        return null;
    }

    // We gaan ervan uit dat als ophalen proxy goed ging, er dan geen verdere RemoteExceptions meer optreden.
    // En gooien daarom RemoteException door als RunTimeException.
    public String meldAan(String aanmeldNaam) {
        try {
            return proxy.meldAan(aanmeldNaam);
        } catch (RemoteException e) {
            throw new ChatJavaException("Client - Exception bij aanmelden:", e);
        }
        return null;
    }
}
