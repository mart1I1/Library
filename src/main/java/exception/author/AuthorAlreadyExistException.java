package exception.author;

public class AuthorAlreadyExistException extends Exception {
    public AuthorAlreadyExistException() {
    }

    public AuthorAlreadyExistException(String message) {
        super(message);
    }
}
