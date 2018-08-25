package exception.user;

public class UserCreateException extends Exception {
    public UserCreateException() {
    }

    public UserCreateException(String message) {
        super(message);
    }
}
