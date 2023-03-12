package chatjava.rmi;

import java.rmi.*;

public interface ChatCallbackInterface extends Remote {
    void chatCallback(String result) throws RemoteException;
}