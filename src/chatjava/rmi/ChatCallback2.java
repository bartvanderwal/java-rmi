package chatjava.rmi;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

import chatjava.*;
import chatjava.client.*;
import chatjava.ChatJavaException;

public class ChatCallback implements ChatCallbackInterface {

    @Override
    public void chatCallback(String result) throws RemoteException {
        System.out.println("Callback resultaat: " + result);
    }

}
