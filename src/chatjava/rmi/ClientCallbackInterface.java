package chatjava.rmi;

import java.rmi.*;

public interface ClientCallbackInterface extends Remote {
    void callback(String message) throws RemoteException;
}