package exception.book;

public class BookDeleteException extends Exception {
    public BookDeleteException() {
    }

    public BookDeleteException(String message) {
        super(message);
    }
}
