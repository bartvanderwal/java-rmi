package chatjava.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloRmiInterface extends Remote {
    public final static String NAAM = "chatjava";

    String sayHello() throws RemoteException;

    String introduce(String name) throws RemoteException;
}

