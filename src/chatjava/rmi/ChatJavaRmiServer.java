package chatjava.rmi;

import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Collectors;

import chatjava.logging.Logger;
import chatjava.server.ServerLogger;
import chatjava.*;

public class ChatJavaRmiServer /* extends UnicastRemoteObject */ implements ChatJavaRmiInterface {
        
    private String serverNaam;

    // TODO: Lijst abonnees uitbreiden van simpele string, naar object met abonnee naam, en evt. authenticatie token.
    private List<Abonnee> abonnees = new ArrayList<>();

    // TODO: Lijst berichten uitbreiden van simpele string, naar object met veld bericht, naam of id abonnee en evt emotie o.i.d.
    // TODO: Nieuw aangemelde clients lijst van alle berichten tot nu toe sturen. Of evt. laten opvragen met nieuwe andere methode dan aanmelden de gemiste o.b.v. timestamp.
    private List<String> berichten = new ArrayList<>();

    private ServerLogger logger;

    public ChatJavaRmiServer(String serverNaam, ServerLogger logger) throws RemoteException {
        this.serverNaam = serverNaam;
        this.logger = logger;
    }

    @Override
    public String zegHallo() {
        return "Hallo, wereld! 😀";
    }

    @Override
    public String meldAan(String naam, ClientCallbackInterface callback) {
        String response = "Hallo " + naam + ", groeten terug van server '" + serverNaam + "'.\n";
        // Voeg abonnee toe, met naam en bijbehorende callback functie and call it.
        // Om onder andere alle berichten tot nu toe ook te versturen van server naar client
        // En op client weer te geven.

        var abonnee = new Abonnee(naam, callback);
        if (!abonnees.stream().map(a -> a.getNaam()).collect(Collectors.toList()).contains(naam)) {
            abonnees.add(abonnee);
            response += "Je bent toegevoegd in lijst abonnees: " + abonneesAlsString();
        } else {
            response += "Aanmelden mislukt. Er staat al een '" + naam + "'' in de lijst abonnees: " + abonneesAlsString();
        }
        logger.info(response);
        return response;
    }

    @Override
    public void chat(String bericht, String aanmeldNaam) {
        var berichtRegel = aanmeldNaam + ": " + bericht;
        this.berichten.add(berichtRegel);
            // Broadcast nieuwe bericht naar alle abonnees
            // Doe dit NIET voor de verzender, want dan echo't server voor hem/haar.
            abonnees.stream().forEach(abonnee -> {
                try {
                    if (!abonnee.getNaam().equals(aanmeldNaam)) {
                        abonnee.getClientCallback().callback(berichtRegel);
                    }
                } catch (RemoteException e) {
                    // throw new ChatJavaException("Probleem tijdens broadcasten van nieuwe bericht naar alle (aangemelde) clients bij client: '" + a.getNaam() + "'.");
                    logger.info("Bericht sturen naar abonnee '" + abonnee.getNaam() + "' mislukt. Deze is waarschijnlijk uitgelogd; haal deze uit de lijst. Error: " + e.getMessage());
                    // abonnees.remove(abonnee);
                }
            });
        logger.info(berichtRegel);
    }

    private String abonneesAlsString() {
        return String.join(", ", abonnees.stream().map(a -> a.getNaam()).collect(Collectors.toList()));
    }
 
}