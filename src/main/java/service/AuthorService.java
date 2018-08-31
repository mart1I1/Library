package service;

import dao.AuthorDAO;
import entity.Author;
import exception.validator.InvalidDataException;
import exception.author.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.Validator;

public class AuthorService {

    private static final Logger logger = LogManager.getLogger(AuthorService.class.getName());

    private AuthorDAO authorDAO;

    AuthorService(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    public Author getAuthorById(int id) throws InvalidDataException, AuthorSQLExecException {
        try {
            Validator.validId(id);
            return authorDAO.getAuthorByID(id);
        } catch (InvalidDataException | AuthorSQLExecException e) {
            logger.error(id, e);
            throw e;
        }
    }

    public Author getAuthorByNameNBirthday(String name, String birthday) throws InvalidDataException, AuthorSQLExecException {
        try {
            Validator.validDate(birthday);
            Validator.validStringValues(name);
            return authorDAO.getAuthorByNameNBirthday(name, birthday);
        } catch (InvalidDataException | AuthorSQLExecException e) {
            logger.error(name + " " + birthday, e);
            throw e;
        }
    }


    public Author createAuthor(Author author) throws InvalidDataException, AuthorSQLExecException, AuthorAlreadyExistException {
        try {
            Validator.validStringValues(author.getName());
            Validator.validDate(author.getBirthday());
            if (getAuthorByNameNBirthday(author.getName(), author.getBirthday()) != null)
                throw new AuthorAlreadyExistException();
            return authorDAO.createAuthor(author);
        } catch (InvalidDataException | AuthorAlreadyExistException | AuthorSQLExecException e) {
            logger.error(author, e);
            throw e;
        }

    }

    public void deleteAuthorById(int id) throws InvalidDataException, AuthorSQLExecException, AuthorNotFoundException {
        try {
            Validator.validId(id);
            if (getAuthorById(id) == null)
                throw new AuthorNotFoundException();
            authorDAO.deleteAuthorById(id);
        } catch (InvalidDataException | AuthorSQLExecException | AuthorNotFoundException e) {
            logger.error(id, e);
            throw e;
        }
    }

}
