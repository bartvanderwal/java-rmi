package chatjava.rmi;

import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import chatjava.logging.Logger;
        
public class HalloRmiServer implements HalloRmiInterface {
        
    private String serverNaam;

    // TODO: Lijst abonnees uitbreiden van simpele string, naar object met abonnee naam, en evt. authenticatie token.
    private List<String> abonnees = new ArrayList<>();

    // TODO: Lijst berichten uitbreiden van simpele string, naar object met veld bericht, naam of id abonnee en evt emotie o.i.d.
    private List<String> berichten = new ArrayList<>();

    public HalloRmiServer(String serverNaam) {
        this.serverNaam = serverNaam;
    }

    public String zegHallo() {
        return "Hallo, wereld! 😀";
    }

    public String meldAan(String naam) {
        String response = "Hallo " + naam + ", groeten terug van server '" + serverNaam + "'.\n";
        if (!abonnees.contains(naam)) {
            abonnees.add(naam);
            response += "Je bent toegevoegd in lijst abonnees: " + abonneesAlsString();
        } else {
            response += "Je stond al in de lijst abonnees: " + abonneesAlsString();
        }
        System.out.println(response);
        return response;

        // TODO Add callback to function and call it.
        // Om onder andere alle berichten tot nu toe ook te versturen van server naar client
        // En op client weer te geven.
    }

    public String abonneesAlsString() {
        return String.join(", ", abonnees);
    }
 
    public void chat(String bericht, String aanmeldNaam) {
        var berichtRegel = aanmeldNaam + ": " + bericht;
        this.berichten.add(berichtRegel);
        System.out.println(berichtRegel);
    }

}