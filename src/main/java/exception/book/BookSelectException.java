package exception.book;

public class BookSelectException extends Exception {
    public BookSelectException() {
    }

    public BookSelectException(String message) {
        super(message);
    }
}
