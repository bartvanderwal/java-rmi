package chatjava;

public class InvalidAanmeldException extends ChatJavaException {

    public InvalidAanmeldException(String foutmeldingPostfix) {
        super("Fout in aanmelding. " + foutmeldingPostfix);
    }

}