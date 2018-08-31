package dao;

import entity.Author;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthorDAOTest {

    private AuthorDAO authorDAO;
    private Author testAuthor1;

    @Before
    public void setUp() throws Exception {
        authorDAO = new AuthorDAO();
        testAuthor1 = new Author("Чарльз Диккенс", "1812-02-07");
    }

    @Test
    public void getAuthorByID() throws Exception {
        Author newAuthor = authorDAO.createAuthor(testAuthor1);
        assertEquals(newAuthor, authorDAO.getAuthorByID(newAuthor.getId()));

        authorDAO.deleteAuthorById(newAuthor.getId());
    }

    @Test
    public void getAuthorByNameNBirthday() throws Exception {
        Author newAuthor = authorDAO.createAuthor(testAuthor1);
        assertEquals(newAuthor, authorDAO.getAuthorByNameNBirthday(newAuthor.getName(), newAuthor.getBirthday()));

        authorDAO.deleteAuthorById(newAuthor.getId());
    }

    @Test
    public void createAuthor() throws Exception {
        assertNull(authorDAO.getAuthorByNameNBirthday(testAuthor1.getName(), testAuthor1.getBirthday()));
        Author newAuthor = authorDAO.createAuthor(testAuthor1);
        assertNotNull(authorDAO.getAuthorByNameNBirthday(testAuthor1.getName(), testAuthor1.getBirthday()));

        authorDAO.deleteAuthorById(newAuthor.getId());
    }

    @Test
    public void deleteAuthorById() throws Exception {
        Author newAuthor = authorDAO.createAuthor(testAuthor1);
        assertNotNull(authorDAO.getAuthorByID(newAuthor.getId()));
        authorDAO.deleteAuthorById(newAuthor.getId());
        assertNull(authorDAO.getAuthorByID(newAuthor.getId()));
    }
}