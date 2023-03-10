package javachat.rmi;

import java.rmi.*;
import java.rmi.registry.*;

public class HelloRmiClient {

    private String host;

    public HelloRmiClient(String host) {
        this.host = host;
        proxy = lookupHelloProxy();
    }

    private HelloRmiInterface proxy;
    
    private HelloRmiInterface lookupHelloProxy() {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HelloRmiInterface proxy = (HelloRmiInterface) registry.lookup(HelloRmiInterface.NAAM);
            return proxy;
       } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println(HelloRmiInterface.NAAM + "niet bekend in RMI Registry: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    // We gaan ervan uit dat als ophalen proxy goed ging, er dan geen verdere RemoteExceptions meer optreden.
    // En vangen deze daarom niet op.
    public String sayHello() {
        try {
            return proxy.sayHello();
        } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
