package dao;

import entity.Book;
import exception.book.BookCreateException;
import exception.book.BookDeleteException;
import exception.book.BookSelectException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BookDAOTest {

    private Book testBook1;
    private Book testBook2;
    private Book testBookZeroQuantity;
    private BookDAO bookDAO;

    @Before
    public void setUp() throws Exception {
        testBook1 = new Book(1, "Гарри Поттер", 1, "Мальчик, который выжил", 1);
        testBook2 = new Book(2, "Моби дик", 2, "Киты и религия", 2);
        testBookZeroQuantity = new Book("Гордость и предубеждение", 3, "Любовь, но не сразу", 0);

        bookDAO = new BookDAO();
    }

    @Test
    public void getExistBooks() throws Exception {
        Book newBook = bookDAO.createBook(testBookZeroQuantity);
        assertEquals(Arrays.asList(testBook1, testBook2), bookDAO.getExistBooks());

        bookDAO.deleteBookById(newBook.getId());
    }

    @Test
    public void getBooks() throws BookCreateException, BookSelectException, BookDeleteException {
        Book newBook = bookDAO.createBook(testBookZeroQuantity);
        assertEquals((Arrays.asList(testBook1, testBook2, newBook)), bookDAO.getBooks());

        bookDAO.deleteBookById(newBook.getId());
    }

    @Test
    public void getBookById() throws BookSelectException {
        assertEquals(testBook1, bookDAO.getBookById(testBook1.getId()));
    }

    @Test
    public void getBookByIdNullResult() throws BookSelectException {
        assertNull(bookDAO.getBookById(testBook2.getId() + 10));
    }

    @Test
    public void getBooksByTitle() throws BookSelectException, BookCreateException, BookDeleteException {
        Book newBookWithSameTitle = bookDAO.createBook(new Book(testBook1.getTitle(), 0, "q", 1));
        assertEquals(Arrays.asList(testBook1, newBookWithSameTitle), bookDAO.getBooksByTitle(testBook1.getTitle()));

        bookDAO.deleteBookById(newBookWithSameTitle.getId());
    }

    @Test
    public void getBookByTitleAndAuthorId() throws BookSelectException {
        assertEquals(testBook1, bookDAO.getBookByTitleAndAuthorId(testBook1.getTitle(), testBook1.getAuthorId()));
    }

    @Test
    public void getBookByTitleAndAuthorIdNull() throws BookSelectException {
        assertNull(bookDAO.getBookByTitleAndAuthorId(testBook1.getTitle(), testBook1.getAuthorId() +1));
    }

    @Test
    public void createBook() throws BookSelectException, BookCreateException, BookDeleteException {
        assertNull(bookDAO.getBookByTitleAndAuthorId(testBookZeroQuantity.getTitle(), testBookZeroQuantity.getAuthorId()));

        Book newBook = bookDAO.createBook(testBookZeroQuantity);
        assertEquals(newBook, bookDAO.getBookByTitleAndAuthorId(testBookZeroQuantity.getTitle(), testBookZeroQuantity.getAuthorId()));

        bookDAO.deleteBookById(newBook.getId());
    }

    @Test
    public void deleteBookById() throws BookCreateException, BookSelectException, BookDeleteException {
        Book newBook = bookDAO.createBook(testBookZeroQuantity);
        assertNotNull(bookDAO.getBookById(newBook.getId()));
        bookDAO.deleteBookById(newBook.getId());
        assertNull(bookDAO.getBookById(newBook.getId()));
    }

    @Test
    public void updateBook() throws Exception {
        Book newBook = bookDAO.createBook(testBookZeroQuantity);
        assertEquals(newBook, bookDAO.getBookById(newBook.getId()));
        newBook.setQuantity(newBook.getQuantity() + 1);
        bookDAO.updateBook(newBook);
        assertEquals(newBook, bookDAO.getBookById(newBook.getId()));

        bookDAO.deleteBookById(newBook.getId());
    }



}