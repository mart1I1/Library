package validator;

import java.util.regex.Pattern;

public class Validator {

    public static boolean validQuantity(int quantity) {
        return quantity >= 0;
    }

    public static boolean validId(int id) {
        return id > 0;
    }

    public static boolean validStringValues(String...args) {
        for (String s : args) {
            if (s == null || s.isEmpty())
                return false;
        }
        return true;
    }

    public static boolean validPassword(String password) {
        if (password == null)
            return false;
        Pattern pattern = Pattern.compile("^.{8,}", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(password).find();
    }

    public static boolean validEmail(String email) {
        if (email == null)
            return false;
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).find();
    }

    public static boolean validAge(int age) {
        return age > 0 && age < 150;
    }
}
