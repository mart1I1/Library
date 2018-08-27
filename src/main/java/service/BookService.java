package service;

import dao.BookDAO;
import entity.Book;
import exception.InvalidDataException;
import exception.author.AuthorNotFoundException;
import exception.author.AuthorSelectException;
import exception.book.*;
import validator.Validator;

import java.util.List;

public class BookService {

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

    public List<Book> getExistBooks() throws BookSelectException {
        return bookDAO.getExistBooks();
    }

    public List<Book> getBooks() throws BookSelectException {
        return bookDAO.getBooks();
    }

    public Book createBook(Book book)
            throws BookAlreadyExistException, BookSelectException, BookCreateException, InvalidDataException, AuthorNotFoundException, AuthorSelectException {
        if (!Validator.validQuantity(book.getQuantity()) ||
                !Validator.validStringValues(book.getTitle(), book.getDescription()) ||
                !Validator.validId(book.getAuthorId())) {
            throw new InvalidDataException();
        }
        if (authorService.getAuthorById(book.getAuthorId()) == null)
            throw new AuthorNotFoundException();
        if (bookDAO.getBookByTitleAndAuthorId(book.getTitle(), book.getAuthorId()) != null)
            throw new BookAlreadyExistException();
        return bookDAO.createBook(book);
    }

    public void deleteBookById(int id)
            throws BookSelectException, BookNotFoundException, BookDeleteException, InvalidDataException {
        if (!Validator.validId(id))
            throw new InvalidDataException("invalid id");
        if (bookDAO.getBookById(id) == null) {
            throw new BookNotFoundException();
        }
        bookDAO.deleteBookById(id);
    }

    public void takeBookById(int id)
            throws BookSelectException, BookNotFoundException, BookUpdateException, BookTakeException, InvalidDataException {
        if (!Validator.validId(id))
            throw new InvalidDataException("invalid id");
        Book book = bookDAO.getBookById(id);
        if (book == null)
            throw new BookNotFoundException();
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            bookDAO.updateBook(book);
        } else {
            throw new BookTakeException("have no books");
        }
    }

    public void returnBookById(int id)
            throws BookNotFoundException, BookSelectException, BookUpdateException, InvalidDataException {
        if (!Validator.validId(id))
            throw new InvalidDataException("invalid id");
        Book book = bookDAO.getBookById(id);
        if (book == null)
            throw new BookNotFoundException();
        book.setQuantity(book.getQuantity() + 1);
        bookDAO.updateBook(book);
    }

    public Book getBookById(int id) throws BookSelectException, InvalidDataException {
        if (!Validator.validId(id))
            throw new InvalidDataException("invalid id");
        return bookDAO.getBookById(id);
    }

    public List<Book> getBookByTitle(String title) throws BookSelectException, InvalidDataException {
        if (!Validator.validStringValues(title))
            throw new InvalidDataException("invalid title");
        return bookDAO.getBooksByTitle(title);
    }
}
