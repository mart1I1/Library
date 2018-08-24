package service;

import dao.BookDAO;
import entity.Author;
import entity.Book;
import exception.InvalidDataException;
import exception.author.AuthorNotFoundException;
import exception.book.BookAlreadyExistException;
import exception.book.BookNotFoundException;
import exception.book.BookTakeException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookService bookService;
    private BookDAO bookDAO;
    private AuthorService authorService;

    private Book testBook1;
    private Book testBook2;
    private Book testBookZeroQuantity;


    @Before
    public void setUp() throws Exception {
        bookDAO = mock(BookDAO.class);
        authorService = mock(AuthorService.class);
        bookService = new BookService(bookDAO, authorService);

        testBook1 = new Book(1, "Гарри Поттер", 1, "Мальчик, который выжил", 1);
        testBook2 = new Book(2, "Моби дик", 2, "Киты и религия", 2);
        testBookZeroQuantity = new Book(3,"Гордость и предубеждение", 3, "Любовь, но не сразу", 0);
    }

    @Test
    public void getExistBooks() throws Exception {
        when(bookDAO.getExistBooks()).thenReturn(Arrays.asList(testBook1, testBook2));
        assertEquals(Arrays.asList(testBook1, testBook2), bookService.getExistBooks());
    }

    @Test
    public void getBooks() throws Exception {
        when(bookDAO.getBooks()).thenReturn(Arrays.asList(testBook1, testBook2, testBookZeroQuantity));
        assertEquals(Arrays.asList(testBook1, testBook2, testBookZeroQuantity), bookService.getBooks());
    }

    @Test(expected = InvalidDataException.class)
    public void createBooknIvalidData() throws Exception {
        Book invalidBook = new Book(1, "", 0, "adsa", 0);
        bookService.createBook(invalidBook);
    }

    @Test(expected = AuthorNotFoundException.class)
    public void createBookAuthorNotFound() throws Exception {
        when(authorService.getAuthorById(anyInt())).thenReturn(null);
        bookService.createBook(testBook1);
    }

    @Test(expected = BookAlreadyExistException.class)
    public void createBookAlreadyExist() throws Exception {
        Author author = mock(Author.class);
        when(authorService.getAuthorById(anyInt())).thenReturn(author);
        when(bookDAO.getBookByTitleAndAuthorId(anyString(), anyInt())).thenReturn(testBook1);
        bookService.createBook(testBook1);
    }

    @Test
    public void createBook() throws Exception {
        Author author = mock(Author.class);
        when(authorService.getAuthorById(anyInt())).thenReturn(author);
        when(bookDAO.getBookByTitleAndAuthorId(anyString(), anyInt())).thenReturn(null);
        when(bookDAO.createBook(testBook1)).thenReturn(testBook1);

        assertEquals(testBook1, bookService.createBook(testBook1));
    }

    @Test(expected = InvalidDataException.class)
    public void deleteBookByIdInvalidId() throws Exception {
        int id = -1;
        bookService.deleteBookById(id);
    }

    @Test(expected = BookNotFoundException.class)
    public void deleteBookByIdBookNotFound() throws Exception {
        when(bookDAO.getBookById(testBook1.getId())).thenReturn(null);
        bookService.deleteBookById(testBook1.getId());
    }

    @Test
    public void deleteBookById() throws Exception {
        when(bookDAO.getBookById(testBook1.getId())).thenReturn(testBook1);
        bookService.deleteBookById(testBook1.getId());
        verify(bookDAO, times(1)).deleteBookById(testBook1.getId());
    }

    @Test(expected = InvalidDataException.class)
    public void takeBookByIdInvalidId() throws Exception {
        int id = -1;
        bookService.takeBookById(id);
    }

    @Test(expected = BookNotFoundException.class)
    public void takeBookByIdNotFound() throws Exception {
        when(bookDAO.getBookById(testBook1.getId())).thenReturn(null);
        bookService.takeBookById(testBook1.getId());
    }

    @Test(expected = BookTakeException.class)
    public void takeBookByIdQuantityBelowZero() throws Exception {
        when(bookDAO.getBookById(testBookZeroQuantity.getId())).thenReturn(testBookZeroQuantity);
        bookService.takeBookById(testBookZeroQuantity.getId());
    }

    @Test
    public void takeBookById() throws Exception {
        when(bookDAO.getBookById(testBook1.getId())).thenReturn(testBook1);
        bookService.takeBookById(testBook1.getId());
        verify(bookDAO, times(1)).getBookById(testBook1.getId());
        verify(bookDAO, times(1)).updateBook(testBook1);
    }

    @Test
    public void returnBookById() {
        
    }

    @Test
    public void getBookById() {
    }

    @Test
    public void getBookByTitle() {
    }
}