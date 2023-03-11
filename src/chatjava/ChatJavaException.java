package chatjava;

public class ChatJavaException extends RuntimeException {
    public ChatJavaException(Exception e) {
        super("Exceptie in ChatJava code.", e);
    }
}