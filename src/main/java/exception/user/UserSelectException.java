package exception.user;

public class UserSelectException extends Exception {
    public UserSelectException() {
    }

    public UserSelectException(String message) {
        super(message);
    }
}
