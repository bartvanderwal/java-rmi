import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloRmiClient {

    // private HelloRmiClient() {}

    private static HelloRmiInterface proxy;
    
    public static void main(String[] args) throws RemoteException {
        // Default host voor RMI Registry, als leeg dan localhost op port 1099.
        var host = (args.length < 1) ? null : args[0];
        var helloClient = new HelloRmiClient();

        proxy = lookupHelloProxy(host);
        var response = helloClient.sayHello();
        System.out.println("response: " + response);
    }

    private static HelloRmiInterface lookupHelloProxy(String host) {
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
    private String sayHello() throws RemoteException {
        return proxy.sayHello();
    }
}