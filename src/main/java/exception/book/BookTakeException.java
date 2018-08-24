package exception.book;

public class BookTakeException extends Exception{
    public BookTakeException() {
    }

    public BookTakeException(String message) {
        super(message);
    }
}
