package exception.author;

public class AuthorSQLExecException extends Exception {
    public AuthorSQLExecException() {
    }

    public AuthorSQLExecException(String message) {
        super(message);
    }
}
