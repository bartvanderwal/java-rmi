package chatjava;

import java.util.*;
import chatjava.logging.*;

public class Io {
 
    private Logger logger;

    public Io(Logger logger) {
        this.logger = logger;
    }

    /**
     *  Inlezen tekst(regel) die gebruiker ingeeft op standaard input (keyboard).
     * @return ingelezen tekst
     */
    public String vraagInput_old(String defaultAlsLeeg, String prompt) {
        System.out.println(prompt);
        var console = System.console();
        if (console == null) {
            throw new ChatJavaException("Unable to fetch console");
        }
        var tekst = console.readLine();
        var resultaat = (tekst==null || tekst.equals("")) ? defaultAlsLeeg : tekst;

        logger.info("vraagInput() - resultaat: '" + resultaat + "'.");
        return resultaat;
    }


    /**
     *  Inlezen tekst(regel) die gebruiker ingeeft op standaard input (keyboard).
     * @return ingelezen tekst
     */
    public String vraagInput(String defaultAlsLeeg, String prompt) {
        System.out.println(prompt);
        // Bron: https://stackoverflow.com/questions/8560395/how-to-use-readline-method-in-java.
        var scanner = new Scanner(System.in);
        var tekst = scanner.nextLine();
        scanner.close();
        var resultaat = (tekst==null || tekst.equals("")) ? defaultAlsLeeg : tekst;

        logger.info("vraagInput() - resultaat: '" + resultaat + "'.");
        return resultaat;
    }

    public String vraagInput(boolean allowEmpty, String prompt) {
        System.out.println(prompt);
        var scanner = new Scanner(System.in);
        var tekst = scanner.nextLine();
        if (!allowEmpty && (tekst == null || tekst == "")) {
            System.out.println("Lege input niet toegestaan!");
            // Nogmaals (recursieve aanroep).
            vraagInput(allowEmpty, prompt); 
        } else {
            tekst = scanner.nextLine();
        }
        scanner.close();
        return tekst;
    }

}