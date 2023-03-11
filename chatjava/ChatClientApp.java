package chatjava;

import chatjava.rmi;

public class ChatClientApp {

    HelloRmiClient helloClient;

    public static void main(String[] args) {
        // Default host voor RMI Registry, als leeg dan localhost op port 1099.
        var host = (args.length < 1) ? null : args[0];

        HelloRmiClient helloClient = new HelloRmiClient(host);

        var response = helloClient.sayHello();
        System.out.println("response: " + response);
    }
}