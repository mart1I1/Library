package service;

import dao.BookDAO;
import entity.Book;
import exception.validator.InvalidDataException;
import exception.author.AuthorNotFoundException;
import exception.author.AuthorSQLExecException;
import exception.book.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.Validator;

import java.util.List;

public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class.getName());

    public BookService() {
        this.bookDAO = new BookDAO();
        authorService = new AuthorService();
    }

    BookService(BookDAO bookDAO, AuthorService authorService) {
        this.bookDAO = bookDAO;
        this.authorService = authorService;
    }

    private AuthorService authorService;
    private BookDAO bookDAO;

    public List<Book> getExistBooks() throws BookSQLExecException {
        try {
            return bookDAO.getExistBooks();
        } catch (BookSQLExecException e) {
            logger.error(e);
            throw e;
        }
    }

    public List<Book> getBooks() throws BookSQLExecException {
        try {
            return bookDAO.getBooks();
        } catch (BookSQLExecException e) {
            logger.error(e);
            throw e;
        }
    }

    public Book createBook(Book book)
            throws BookAlreadyExistException, AuthorSQLExecException, InvalidDataException, AuthorNotFoundException, BookSQLExecException {
        try {
            Validator.validBook(book);
            if (authorService.getAuthorById(book.getAuthorId()) == null)
                throw new AuthorNotFoundException();
            if (bookDAO.getBookByTitleAndAuthorId(book.getTitle(), book.getAuthorId()) != null)
                throw new BookAlreadyExistException();
            return bookDAO.createBook(book);
        } catch (InvalidDataException | AuthorSQLExecException | BookSQLExecException | AuthorNotFoundException | BookAlreadyExistException e) {
            logger.error(book, e);
            throw e;
        }

    }

    public void deleteBookById(int id) throws InvalidDataException, BookNotFoundException, BookSQLExecException {
        try {
            Validator.validId(id);
            if (bookDAO.getBookById(id) == null) {
                throw new BookNotFoundException();
            }
            bookDAO.deleteBookById(id);
        } catch (InvalidDataException | BookSQLExecException | BookNotFoundException e) {
            logger.error(id, e);
            throw e;
        }
    }

    public void takeBookById(int id)
            throws BookNotFoundException, BookTakeException, InvalidDataException, BookSQLExecException {
        try {
            Validator.validId(id);
            Book book = bookDAO.getBookById(id);
            if (book == null)
                throw new BookNotFoundException();
            if (book.getQuantity() > 0) {
                book.setQuantity(book.getQuantity() - 1);
                bookDAO.updateBook(book);
            } else {
                throw new BookTakeException("have no books");
            }
        } catch (InvalidDataException | BookSQLExecException | BookNotFoundException | BookTakeException e) {
            logger.error(id, e);
            throw e;
        }
    }

    public void returnBookById(int id) throws InvalidDataException, BookNotFoundException, BookSQLExecException {
        try {
            Validator.validId(id);
            Book book = bookDAO.getBookById(id);
            if (book == null)
                throw new BookNotFoundException();
            book.setQuantity(book.getQuantity() + 1);
            bookDAO.updateBook(book);
        } catch (InvalidDataException | BookSQLExecException | BookNotFoundException e) {
            logger.error(id, e);
            throw e;
        }
    }

    public Book getBookById(int id) throws InvalidDataException, BookSQLExecException {
        try {
            Validator.validId(id);
            return bookDAO.getBookById(id);
        } catch (InvalidDataException | BookSQLExecException e) {
            logger.error(id, e);
            throw e;
        }
    }

    public List<Book> getBookByTitle(String title) throws InvalidDataException, BookSQLExecException {
        try {
            Validator.validStringValues(title);
            return bookDAO.getBooksByTitle(title);
        } catch (InvalidDataException | BookSQLExecException e) {
            logger.error(title, e);
            throw e;
        }
    }
}
