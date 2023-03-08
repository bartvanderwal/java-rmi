import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloRmiInterface extends Remote {
    String sayHello() throws RemoteException;
}

