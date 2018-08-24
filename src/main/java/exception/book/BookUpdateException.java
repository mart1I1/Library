package exception.book;

public class BookUpdateException extends Exception {
    public BookUpdateException() {
    }

    public BookUpdateException(String message) {
        super(message);
    }
}
