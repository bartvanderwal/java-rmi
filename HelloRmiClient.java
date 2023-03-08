import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloRmiClient {

    private HelloRmiClient() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HelloRmiInterface proxy = (HelloRmiInterface) registry.lookup(HelloRmiInterface.NAAM);
            String response = proxy.sayHello();
            System.out.println("response: " + response);
        } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println(HelloRmiInterface.NAAM + "niet bekend in RMI Registry: " + e.toString());
            e.printStackTrace();
        }
    }
}