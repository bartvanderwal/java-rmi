package chatjava;

import java.io.*;
import java.util.*;
import chatjava.logging.*;

public class Io {
 
    private Logger logger;

    Console console;

    public Io(Logger logger) {
        this.logger = logger;
        console = System.console();
        if (console == null) {
            throw new ChatJavaException("Unable to fetch console");
        }

    }

    /**
     *  Inlezen tekst(regel) die gebruiker ingeeft op standaard input (keyboard).
     * @return ingelezen tekst
     */
    public String vraagInput(String defaultAlsLeeg, String prompt) {
        System.out.println(prompt);
        var tekst = console.readLine();
        var resultaat = (tekst==null || tekst.equals("")) ? defaultAlsLeeg : tekst;

        return resultaat;
    }

    public String vraagInput(boolean allowEmpty, String prompt) {
        System.out.println(prompt);
        var tekst = console.readLine();
        // var tekst = scanner.nextLine();
        if (!allowEmpty && (tekst == null || tekst.equals(""))) {
            System.out.println("Lege input niet toegestaan!");
            // Nogmaals (recursieve aanroep).
            tekst = vraagInput(allowEmpty, prompt); 
        }
        return tekst;
    }

}