package chatjava.rmi;

import java.rmi.*;
import java.rmi.registry.*;
import chatjava.*;
import chatjava.client.*;

import chatjava.ChatJavaException;

public class HalloRmiClient {

    private String host;

    private Io io;

    // Voorlopig is logLevel op client standaard info.
    // TODO: Evt. ook een `client.properties` toevoegen met instelbaar logLevel erin zoals voor server app.
    private ClientLogger logger;


    public HalloRmiClient(String host) {
        this.host = host;
        proxy = lookupHalloProxy();
        logger = new ClientLogger(null);
        this.io = new Io(logger);
    }

    private String aanmeldNaam;

    private HalloRmiInterface proxy;
    
    private HalloRmiInterface lookupHalloProxy() {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HalloRmiInterface proxy = (HalloRmiInterface) registry.lookup(HalloRmiInterface.NAAM);
            return proxy;
        } catch (RemoteException e) {
            throw new ChatJavaException("Remote exception op Chat Client", e);
        } catch (NotBoundException e) {
            throw new ChatJavaException("Client: " + HalloRmiInterface.NAAM + " interface niet bekend in RMI Registry.", e);
        }
    }

    public void startDialoog() {
        System.out.println("Chat client gestart.");

        var halloWereldResponse = zegHallo();
        logger.info("ChatServer draait! Response: " + halloWereldResponse);

        var prompt = "Geef een aanmeldnaam om je mee aan te melden bij chatserver.";
        var aanmeldNaam = io.vraagInput(false, prompt);

        var response = meldAan(aanmeldNaam);
        logger.info(response);

        var bericht = "";        
        while (bericht!="stop") {
            bericht = io.vraagInput(false, "Tik een chat bericht en <enter> om te verzenden ('stop' om te stoppen).");

            // TODO Verstuur bericht en handel response/callback af.
            chat(bericht);
        }
    }

    // We gaan ervan uit dat als ophalen proxy goed ging, er dan geen verdere RemoteExceptions meer optreden.
    // En gooien daarom RemoteException door als RunTimeException.
    private String zegHallo() {
        try {
            return proxy.zegHallo();
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

    private void chat(String bericht) {
        if (this.aanmeldNaam != "") {
            throw new InvalidAanmeldException("Je verstuur een bericht, maar bent nog niet aangemeld.");
        }
        try {
            proxy.chat(bericht, this.aanmeldNaam);
        } catch (RemoteException e) {
            throw new ChatJavaException("Client - Exception bij versturen bericht:" + bericht, e);
        }
    }

}
