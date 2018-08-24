package exception.book;

import database.executor.Executor;

public class BookAlreadyExistException extends Exception {
    public BookAlreadyExistException() {
    }

    public BookAlreadyExistException(String message) {
        super(message);
    }
}
