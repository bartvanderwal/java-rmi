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
    public String vraagInput(String defaultAlsLeeg, String prompt, boolean newLine) {
        if (newLine) {
            System.out.println(prompt);
        } else {
            System.out.print(prompt);
        }
        var tekst = console.readLine();
        var resultaat = (tekst==null || tekst.equals("")) ? defaultAlsLeeg : tekst;

        return resultaat;
    }

    // Overload, met default newLine = true
    public String vraagInput(String defaultAlsLeeg, String prompt) {
        return vraagInput(defaultAlsLeeg, prompt, true);
    }

    // Overload, met default newLine = true
    public String vraagInput(boolean allowEmpty, String prompt) {
        return vraagInput(allowEmpty, prompt, true);
    }

    public String vraagInput(boolean allowEmpty, String prompt, boolean newLine) {
        if (newLine) {
            System.out.println(prompt);
        } else {
            System.out.print(prompt);
        }
        var tekst = console.readLine();
        // var tekst = scanner.nextLine();
        if (!allowEmpty && (tekst == null || tekst.equals(""))) {
            System.out.println("Lege input niet toegestaan!");
            // Nogmaals (recursieve aanroep).
            tekst = vraagInput(allowEmpty, prompt, newLine); 
        }
        return tekst;
    }

}