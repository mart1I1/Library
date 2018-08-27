package service;

import dao.AuthorDAO;
import entity.Author;
import exception.InvalidDataException;
import exception.author.*;
import validator.Validator;

import java.util.Date;

public class AuthorService {

    private AuthorDAO authorDAO;

    AuthorService(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    public Author getAuthorById(int id) throws InvalidDataException, AuthorSelectException {
        if (!Validator.validId(id))
            throw new InvalidDataException();
        return authorDAO.getAuthorByID(id);
    }

    public Author getAuthorByNameNBirthday(String name, String birthday) throws InvalidDataException, AuthorSelectException {
        if (!Validator.validStringValues(name) || !Validator.validDate(birthday))
            throw new InvalidDataException();
        return authorDAO.getAuthorByNameNBirthday(name, birthday);
    }


    public Author createAuthor(Author author)
            throws InvalidDataException, AuthorSelectException, AuthorAlreadyExistException, AuthorCreateException {
        if (!Validator.validStringValues(author.getName()) || !Validator.validDate(author.getBirthday()))
            throw new InvalidDataException();
        if (getAuthorByNameNBirthday(author.getName(), author.getBirthday()) != null)
            throw new AuthorAlreadyExistException();
        return authorDAO.createAuthor(author);

    }

    public void deleteAuthorById(int id) throws InvalidDataException, AuthorSelectException, AuthorNotFoundException, AuthorDeleteException {
        if (!Validator.validId(id))
            throw new InvalidDataException("bad id");
        if (getAuthorById(id) == null)
            throw new AuthorNotFoundException();
        authorDAO.deleteAuthorById(id);
    }

}
