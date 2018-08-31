package validator;

import entity.Book;
import entity.User;
import exception.validator.InvalidDataException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

public class Validator {

    public static boolean validQuantity(int quantity) throws InvalidDataException {
        if (quantity >= 0)
            return true;
        else throw new InvalidDataException("quantity");
    }

    public static boolean validId(int id) throws InvalidDataException {
        if (id > 0)
            return true;
        else throw new InvalidDataException("id");
    }

    public static boolean validStringValues(String...args) throws InvalidDataException {
        for (String s : args) {
            if (s == null || s.isEmpty())
                throw new InvalidDataException("string value");
        }
        return true;
    }

    public static boolean validPassword(String password) throws InvalidDataException {
        if (password == null)
            throw new InvalidDataException("password");
        Pattern pattern = Pattern.compile("^.{8,}", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(password).find())
            return true;
        else
            throw new InvalidDataException("password");
    }

    public static boolean validEmail(String email) throws InvalidDataException {
        if (email == null)
            throw new InvalidDataException("email");;
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(email).find())
            return true;
        else
            throw new InvalidDataException("email");
    }

    public static boolean validAge(int age) throws InvalidDataException {
        if (age > 0 && age < 150)
            return true;
        else
            throw new InvalidDataException("age");
    }

    public static boolean validUser(User user) throws InvalidDataException {
        Validator.validAge(user.getAge());
        Validator.validPassword(user.getPassword());
        Validator.validStringValues(user.getName());
        Validator.validEmail(user.getEmail());
        return true;
    }

    public static boolean validBook(Book book) throws InvalidDataException {
        Validator.validQuantity(book.getQuantity());
        Validator.validStringValues(book.getTitle(), book.getDescription());
        Validator.validId(book.getAuthorId());
        return true;
    }

    //TODO: Доделать!
    public static boolean validDate(String date) throws InvalidDataException {
        if (date == null)
            throw new InvalidDataException("date");
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        if (pattern.matcher(date).find())
            return true;
        else
            throw new InvalidDataException("date");
    }

}
