package chatjava.rmi;

import java.rmi.registry.Registry;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
        
public class HalloRmiServer implements HalloRmiInterface {
        
    private String serverNaam;

    private List<String> abonnees = new ArrayList<>();

    public HalloRmiServer(String serverNaam) {
        this.serverNaam = serverNaam;
    }

    public String zegHallo() {
        return "Hallo, wereld! 😀";
    }

    public String meldAan(String naam) {
        String response = "Hallo " + naam + ", groeten terug van server" + serverNaam + "\n.";
        if (!abonnees.contains(naam)) {
            abonnees.add(naam);
            response += "Je bent toegevoegd in lijst abonnees.";
        } else {
            response += "Je stond al in de lijst abonnees.";
        }
        return response;

        // TODO Add callback to function and call it.
    }
 
}