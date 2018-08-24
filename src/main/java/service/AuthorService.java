package service;

import dao.AuthorDAO;
import entity.Author;

public class AuthorService {

    private AuthorDAO authorDAO;

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    public Author getAuthorById(int id) {
        return authorDAO.getAuthorByID(id);
    }

}
