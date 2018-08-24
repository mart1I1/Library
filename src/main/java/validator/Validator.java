package validator;

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

}
