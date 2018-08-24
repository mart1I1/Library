package exception.author;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException() {
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
