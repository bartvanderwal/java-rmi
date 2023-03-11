package chatjava;

import chatjava.rmi.*;

public class ChatClientApp {

    HalloRmiClient helloClient;

    public static void main(String[] args) {
        // Default host voor RMI Registry, als leeg dan localhost op port 1099.
        var host = (args.length < 1) ? null : args[0];

        var halloClient = new HalloRmiClient(host);

        var response = halloClient.zegHallo();
        Logger.info("Response: " + response);
    }
}