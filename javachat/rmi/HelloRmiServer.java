package javachat.rmi;

import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
        
public class HelloRmiServer implements HelloRmiInterface {
        
    public HelloRmiServer() {}

    public String sayHello() {
        return "Hello, world! 😀";
    }       
 
}