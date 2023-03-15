package chatjava.rmi;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ChatCallback /** extends UnicastRemoteObject **/ implements ChatCallbackInterface, Remote {
    public ChatCallback() throws RemoteException {
        super();
    }

    @Override
    public void chatCallback(String result) throws RemoteException {
        System.out.println("Resultaat: " + result);
    }
}
