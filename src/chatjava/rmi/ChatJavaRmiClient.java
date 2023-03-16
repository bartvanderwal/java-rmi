package chatjava.rmi;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

import chatjava.*;
import chatjava.client.*;
import chatjava.ChatJavaException;

public class ChatJavaRmiClient extends UnicastRemoteObject implements ClientCallbackInterface {

    private String host;

    private String aanmeldNaam;

    private Io io;

    // Voorlopig is logLevel op client standaard info.
    // TODO: Evt. ook een `client.properties` toevoegen met instelbaar logLevel erin zoals voor server app.
    private ClientLogger logger;

    private ChatJavaRmiInterface proxy;

    public ChatJavaRmiClient(String host) throws RemoteException {
        this.host = host;

        logger = new ClientLogger(null);

        // TODO: Dit kan ook weg, omdat ClientCallbackInterface implementatie ChatJavaRmiClient nu ook UniCastRemoteObject implementeert. 
        // Bind the remote object's stub in the registry.
        // var nameOfRemoteReference = "chatCallback";
        // logger.info("Chat Client: " + nameOfRemoteReference + " registreren bij registry.");
        // Registry registry = LocateRegistry.getRegistry(host);
        // ClientCallbackInterface skeleton = (ClientCallbackInterface) UnicastRemoteObject.exportObject(chatCallback, 0);
        // registry.bind(nameOfRemoteReference, skeleton);
        // // Einde TODO.
        try {
            proxy = lookupHalloProxy(host);
        } catch (RemoteException e) {
            var foutBericht = "Remote exception op Chat Client; het lijkt dat de chat server NIET draait op " + host;
            logger.info(foutBericht);
            throw new ChatJavaException(foutBericht);
        }
        this.io = new Io(logger);
    }
    
    private static ChatJavaRmiInterface lookupHalloProxy(String host) throws RemoteException {
        // ChatJavaRmiInterface proxy = null;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            
            var proxy = (ChatJavaRmiInterface) registry.lookup(ChatJavaRmiInterface.NAAM);
            return proxy;
        } catch (NotBoundException e) {
            throw new ChatJavaException("Client: " + ChatJavaRmiInterface.NAAM + " interface niet bekend in RMI Registry.", e);
        }
    }

    public void startChatten() throws RemoteException {
        System.out.println("Chat client gestart.");

        var halloWereldResponse = proxy.zegHallo();
        
        logger.info("ChatServer draait! Response: " + halloWereldResponse);

        var prompt = "Geef een aanmeldnaam om je mee aan te melden bij chatserver.";
        var aanmeldNaam = io.vraagInput(false, prompt);

        // logger.info("Gelezen aanmeldNaam: " + aanmeldNaam);
        var response = meldAan(aanmeldNaam);
        logger.info("Chat Server: " + response);
        var initialPrompt = "Tik een chat bericht en <enter> om te verzenden ('stop' om te stoppen).";
        logger.info(initialPrompt);
        var bericht = "";
        while (!bericht.equals("stop")) {
            bericht = io.vraagInput(false, ">", false);

            // Verstuur bericht en handel response/callback af.
            chat(bericht, this.aanmeldNaam);
        }
        logger.info("Chat client gestopt. Tot de volgende keer!");
    }

    private String meldAan(String aanmeldNaam) {
        if (aanmeldNaam=="" || aanmeldNaam==null) {
            throw new InvalidAanmeldException("Aanmeldnaam mag niet leeg zijn.");
        }
        if (this.aanmeldNaam != null) {
            throw new InvalidAanmeldException("Je bent al aangemeld; mag niet een tweede keer.");
        }
        this.aanmeldNaam = aanmeldNaam;
        try {
            return proxy.meldAan(aanmeldNaam, this);
        } catch (RemoteException e) {
            throw new ChatJavaException("Client - Exception bij aanmelden:", e);
        }
    }

    private void chat(String bericht, String aanmeldNaam) {
        if (this.aanmeldNaam == "") {
            throw new InvalidAanmeldException("Je verstuurt een bericht, maar bent nog niet aangemeld.");
        }
        try {
            proxy.chat(bericht, this.aanmeldNaam);
        } catch (RemoteException e) {
            throw new ChatJavaException("Client - Exception bij versturen bericht:" + bericht, e);
        }
        // try {
        //     chatCallback.onCallback("Hallo terug!");
        // } catch (RemoteException e) {
        //     throw new ChatJavaException("Client - Exception bij uitvoeren callback:" + bericht, e);
        // }
    }

    @Override
    public void callback(String result) throws RemoteException {
        System.out.println("<-" + result);
    }

}
