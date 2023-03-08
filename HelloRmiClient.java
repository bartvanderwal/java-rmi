import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloRmiClient {

    private HelloRmiClient() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HelloRmiInterface stub = (HelloRmiInterface) registry.lookup("rmi101");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}