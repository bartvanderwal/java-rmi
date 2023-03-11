package chatjava.rmi;

import java.rmi.*;
import java.rmi.registry.*;
import chatjava.*;

import chatjava.ChatJavaException;

public class HalloRmiClient {

    private String host;

    public HalloRmiClient(String host) {
        this.host = host;
        proxy = lookupHalloProxy();
    }

    private String aanmeldNaam;

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
    }

    public String meldAan(String aanmeldNaam) {
        if (aanmeldNaam=="" || aanmeldNaam==null) {
            throw new InvalidAanmeldException("Aanmeldnaam mag niet leeg zijn.");
        }
        if (this.aanmeldNaam != null) {
            throw new InvalidAanmeldException("Je bent al aangemeld; mag niet een tweede keer.");
        }
        this.aanmeldNaam = aanmeldNaam;
        try {
            return proxy.meldAan(aanmeldNaam);
        } catch (RemoteException e) {
            throw new ChatJavaException("Client - Exception bij aanmelden:", e);
        }
    }

    public void chat(String bericht) {
        if (this.aanmeldNaam != "") {
            throw new InvalidAanmeldException("Je verstuur een bericht, maar bent nog niet aangemeld.");
        }
        try {
            proxy.chat(bericht, this.aanmeldNaam);
        } catch (RemoteException e) {
            throw new ChatJavaException("Client - Exception bij versturen bericht:" + bericht, e);
        }
    }

}
