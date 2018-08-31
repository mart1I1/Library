package exception.user;

public class UserSQLExecException extends Exception {
    public UserSQLExecException() {
    }

    public UserSQLExecException(String message) {
        super(message);
    }
}
