package exception.book;

public class BookSQLExecException extends Exception {
    public BookSQLExecException() {
    }

    public BookSQLExecException(String message) {
        super(message);
    }
}
