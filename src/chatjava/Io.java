package chatjava;

import java.util.*;

public class Io {
    /**
     *  Inlezen tekst(regel) die gebruiker ingeeft op standaard input (keyboard).
     * @return ingelezen tekst
     */
    public String vraagInput() {
        // Bron: https://stackoverflow.com/questions/8560395/how-to-use-readline-method-in-java.
        var scanner = new Scanner(System.in);
        var tekst = scanner.next();
        return tekst;
    }
}