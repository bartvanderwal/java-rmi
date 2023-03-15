package chatjava.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatJavaRmiInterface extends Remote {

    public final static String NAAM = "chatjava";

    String zegHallo() throws RemoteException;

    String meldAan(String name, ClientCallbackInterface callback) throws RemoteException;

    void chat(String bericht, String aanmeldNaam) throws RemoteException;
}

