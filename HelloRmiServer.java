import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
        
public class HelloRmiServer implements HelloRmiInterface {
        
    public HelloRmiServer() {}

    public String sayHello() {
        return "Hello, world! 😀";
    }
        
    public static void main(String args[]) {
        
        try {
            HelloRmiServer obj = new HelloRmiServer();
            HelloRmiInterface skeleton = (HelloRmiInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            try {
                registry.bind(HelloRmiInterface.NAAM, skeleton);
            } catch (AlreadyBoundException e) {
                System.err.println("Interface " + HelloRmiInterface.NAAM + " already bound");
            }
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}