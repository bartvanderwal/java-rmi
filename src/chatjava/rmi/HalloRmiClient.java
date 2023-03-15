package chatjava.rmi;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

import chatjava.*;
import chatjava.client.*;
import chatjava.ChatJavaException;

public class HalloRmiClient /** extends UnicastRemoteObject implements ChatCallbackInterface **/ {

    private String host;

    private String aanmeldNaam;

    private Io io;

    // Voorlopig is logLevel op client standaard info.
    // TODO: Evt. ook een `client.properties` toevoegen met instelbaar logLevel erin zoals voor server app.
    private ClientLogger logger;

    private HalloRmiInterface proxy;

    public HalloRmiClient(String host) throws RemoteException {
        this.host = host;

        logger = new ClientLogger(null);

        // TODO: Here be dragons?
        // Bind the remote object's stub in the registry.
        var nameOfRemoteReference = "chatCallback";
        logger.info("Chat Client: " + nameOfRemoteReference + " registreren bij registry.");
        Registry registry = LocateRegistry.getRegistry(host);
        var chatCallback = new ChatCallback();
        ChatCallbackInterface skeleton = (ChatCallbackInterface) UnicastRemoteObject.exportObject(chatCallback, 0);
        registry.rebind(nameOfRemoteReference, skeleton);
        // // Einde TODO.

        proxy = lookupHalloProxy();
        this.io = new Io(logger);
    }
    
    private HalloRmiInterface lookupHalloProxy() {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            
            proxy = (HalloRmiInterface) registry.lookup(HalloRmiInterface.NAAM);
            return proxy;
        } catch (RemoteException e) {
            throw new ChatJavaException("Remote exception op Chat Client", e);
        } catch (NotBoundException e) {
            throw new ChatJavaException("Client: " + HalloRmiInterface.NAAM + " interface niet bekend in RMI Registry.", e);
        }
    }

    public void startDialoog() throws RemoteException {
        System.out.println("Chat client gestart.");

        proxy.zegHallo(r -> chatCallback.chatCallback(r));
        
        logger.info("ChatServer draait! Response: " + halloWereldResponse);

        var prompt = "Geef een aanmeldnaam om je mee aan te melden bij chatserver.";
        var aanmeldNaam = io.vraagInput(false, prompt);

        // logger.info("Gelezen aanmeldNaam: " + aanmeldNaam);
        var response = meldAan(aanmeldNaam);
        logger.info("Response aanmelding server: " + response);

        var bericht = "";        
        while (!bericht.equals("stop")) {
            bericht = io.vraagInput(false, "Tik een chat bericht en <enter> om te verzenden ('stop' om te stoppen).");

            // Verstuur bericht en handel response/callback af.
            chat(bericht, this.aanmeldNaam);
        }
        logger.info("Chat client gestopt. Tot de volgende keer!");
    }

    private ChatCallback chatCallback = new ChatCallback();

    // We gaan ervan uit dat als ophalen proxy goed ging, er dan geen verdere RemoteExceptions meer optreden.
    // En gooien daarom RemoteException door als RunTimeException.
    private String zegHallo() {
        try {
            return proxy.zegHallo(r -> chatCallback.chatCallback(r));
        } catch (RemoteException e) {
            throw new ChatJavaException("Client exception: " + e.toString(), e);
        }
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
            return proxy.meldAan(aanmeldNaam);
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

    // @Override
    // public void chatCallback(String result) throws RemoteException {
    //     System.out.println("Callback resultaat: " + result);
    // }

}
