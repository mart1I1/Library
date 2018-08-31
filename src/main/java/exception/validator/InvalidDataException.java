package exception.validator;

public class InvalidDataException extends Exception {
    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
