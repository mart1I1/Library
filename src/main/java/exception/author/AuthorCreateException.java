package exception.author;

public class AuthorCreateException extends Exception {
    public AuthorCreateException() {
    }

    public AuthorCreateException(String message) {
        super(message);
    }
}
