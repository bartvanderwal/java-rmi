package javachat.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloRmiInterface extends Remote {
    public final static String NAAM = "rmi101";

    String sayHello() throws RemoteException;
}

