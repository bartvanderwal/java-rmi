package chatjava;

import chatjava.rmi.ClientCallbackInterface;

// Dit is een value object.
public class Abonnee {

    private final String naam;

    private final ClientCallbackInterface clientCallback;

    public Abonnee(String naam, ClientCallbackInterface clientCallback) {
        this.naam = naam;
        this.clientCallback = clientCallback;
    }

    public String getNaam() {
        return naam;
    }

    public ClientCallbackInterface getClientCallback() {
        return clientCallback;
    }
}