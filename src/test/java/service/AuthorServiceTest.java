package service;

import dao.AuthorDAO;
import entity.Author;
import exception.InvalidDataException;
import exception.author.AuthorAlreadyExistException;
import exception.author.AuthorNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    private AuthorDAO authorDAO;
    private AuthorService authorService;

    private Author testAuthor1;
    private Author testAuthor2;

    @Before
    public void setUp() throws Exception {
        testAuthor1 = new Author(3,"Чарльз Диккенс", "1812-02-07");
        testAuthor2 = new Author(4,"Анна Андреевна Ахматова", "1889-06-23");

        authorDAO = mock(AuthorDAO.class);
        authorService = new AuthorService(authorDAO);
    }

    @Test(expected = InvalidDataException.class)
    public void getAuthorByIdInvalidId() throws Exception {
        authorService.getAuthorById(-1);
    }

    @Test
    public void getAuthorById() throws Exception {
        authorService.getAuthorById(testAuthor1.getId());
        verify(authorDAO, times(1)).getAuthorByID(testAuthor1.getId());
    }

    @Test(expected = InvalidDataException.class)
    public void getAuthorByNameNBirthdayInvalidDate() throws Exception {
        Author invalidAuthor = new Author("anybody", "1555-1206");
        authorService.getAuthorByNameNBirthday(invalidAuthor.getName(), invalidAuthor.getBirthday());
    }

    @Test(expected = InvalidDataException.class)
    public void getAuthorByNameNBirthdayEmptyName() throws Exception {
        Author invalidAuthor = new Author("", "1555-1206");
        authorService.getAuthorByNameNBirthday(invalidAuthor.getName(), invalidAuthor.getBirthday());
    }

    @Test(expected = InvalidDataException.class)
    public void getAuthorByNameNBirthdayNullName() throws Exception {
        Author invalidAuthor = new Author(null, "1555-1206");
        authorService.getAuthorByNameNBirthday(invalidAuthor.getName(), invalidAuthor.getBirthday());
    }

    @Test
    public void getAuthorByNameNBirthday() throws Exception {
        authorService.getAuthorByNameNBirthday(testAuthor1.getName(), testAuthor1.getBirthday());
        verify(authorDAO, times(1))
                .getAuthorByNameNBirthday(testAuthor1.getName(), testAuthor1.getBirthday());
    }

    @Test(expected = InvalidDataException.class)
    public void createAuthorEmptyName() throws Exception {
        Author invalidAuthor = new Author("", "1995-11-29");
        authorService.createAuthor(invalidAuthor);
    }

    @Test(expected = InvalidDataException.class)
    public void createAuthorInvalidBirthday() throws Exception {
        Author invalidAuthor = new Author("somebody", "199511-29");
        authorService.createAuthor(invalidAuthor);
    }

    @Test(expected = AuthorAlreadyExistException.class)
    public void createAuthorAlreadyExist() throws Exception {
        when(authorDAO.getAuthorByNameNBirthday(testAuthor1.getName(), testAuthor1.getBirthday())).thenReturn(testAuthor1);
        authorService.createAuthor(testAuthor1);
    }

    @Test
    public void createAuthor() throws Exception {
        when(authorDAO.getAuthorByNameNBirthday(anyString(), anyString())).thenReturn(null);
        authorService.createAuthor(testAuthor1);
        verify(authorDAO, times(1)).createAuthor(testAuthor1);
    }

    @Test(expected = InvalidDataException.class)
    public void deleteAuthorByIdInvalidId() throws Exception {
        authorService.deleteAuthorById(-1);
    }

    @Test(expected = AuthorNotFoundException.class)
    public void deleteAuthorByIdNotFound() throws Exception {
        when(authorDAO.getAuthorByID(anyInt())).thenReturn(null);
        authorService.deleteAuthorById(testAuthor1.getId());
    }

    @Test
    public void deleteAuthor() throws Exception {
        when(authorDAO.getAuthorByID(anyInt())).thenReturn(testAuthor1);
        authorService.deleteAuthorById(testAuthor1.getId());
        verify(authorDAO, times(1)).deleteAuthorById(testAuthor1.getId());
    }
}