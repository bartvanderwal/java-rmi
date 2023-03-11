package chatjava.rmi;

import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
        
public class HelloRmiServer implements HelloRmiInterface {
        
    private String serverName;

    private List<String> introducees = new ArayList<>();

    public HelloRmiServer(String serverName) {
        this.serverName = serverName;
    }

    public String sayHello() {
        return "Hallo, wereld! 😀";
    }

    public String introduce(String name) {
        String response = "Hallo " + name + ", groeten terug van server" + serverName;
        if (!introducees.contains(name)) 
            this.introducees.Add(name);
            response += "\n You'"
        }
        return response;
        // TODO Add callback to function and call it.
    }
 
}