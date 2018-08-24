package exception.book;

public class BookCreateException extends Exception {
    public BookCreateException() {
    }

    public BookCreateException(String message) {
        super(message);
    }
}
