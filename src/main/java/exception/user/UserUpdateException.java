package exception.user;

public class UserUpdateException extends Exception {
    public UserUpdateException() {
    }

    public UserUpdateException(String message) {
        super(message);
    }
}
