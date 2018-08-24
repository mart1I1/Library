package dao;

import database.executor.Executor;
import entity.Book;
import exception.book.BookCreateException;
import exception.book.BookDeleteException;
import exception.book.BookSelectException;
import exception.book.BookUpdateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDAO {

    private static final Logger logger = LogManager.getLogger(BookDAO.class.getName());

    private final static String GET_EXIST_BOOKS = "SELECT * FROM book where quantity > 0";
    private final static String GET_BOOK_BY_ID = "SELECT * FROM book where id = ?";
    private final static String GET_BOOKS = "SELECT * FROM book";
    private final static String GET_BOOK_BY_TITLE_N_AUTHORID = "SELECT * FROM book where title = ? and authorId = ?";
    private final static String GET_BOOKS_BY_TITLE = "SELECT * FROM book where title = ?";

    private final static String CREATE_BOOK = "INSERT INTO book (title, authorId, description, quantity) " +
            "VALUES(?, ?, ?, ?)";
    private final static String DELETE_BOOK_BY_ID = "DELETE FROM book where id = ?";
    private final static String UPDATE_BOOK = "UPDATE book " +
            "SET title = ?, authorId = ?, description = ?, quantity = ? where id = ?";

    private Executor executor;

    public BookDAO() {
        this.executor = new Executor();
    }

    public List<Book> getExistBooks() throws BookSelectException {
        List<?> param = prepareParam();
        try {
            return execAndGetListBooks(GET_EXIST_BOOKS, param);
        } catch (SQLException e) {
            logger.error("getExistBooks: " + e.getSQLState());
            throw new BookSelectException();
        }
    }

    public List<Book> getBooks() throws BookSelectException {
        List<?> param = prepareParam();
        try {
            return execAndGetListBooks(GET_BOOKS, param);
        } catch (SQLException e) {
            logger.error("getBooks: " + e.getSQLState() );
            throw new BookSelectException();
        }
    }

    public Book getBookById(int id) throws BookSelectException {
        List<?> param = prepareParam(id);
        try {
            return execAndGetBook(GET_BOOK_BY_ID, param);
        } catch (SQLException e) {
            throw new BookSelectException();
        }
    }

    public List<Book> getBooksByTitle(String title) throws BookSelectException {
        List<?> param = prepareParam(title);
        try {
            return execAndGetListBooks(GET_BOOKS_BY_TITLE, param);
        } catch (SQLException e) {
            throw new BookSelectException();
        }
    }

    public Book getBookByTitleAndAuthorId(String title, int authorId) throws BookSelectException {
        List<?> param = prepareParam(title, authorId);
        try {
            return execAndGetBook(GET_BOOK_BY_TITLE_N_AUTHORID, param);
        } catch (SQLException e) {
            throw new BookSelectException();
        }
    }

    public Book createBook(Book book) throws BookCreateException {
        List<?> param = prepareParam(
                book.getTitle(),
                book.getAuthorId(),
                book.getDescription(),
                book.getQuantity());
        try {
            executor.execUpdate(CREATE_BOOK, param);
            return getBookByTitleAndAuthorId(book.getTitle(), book.getAuthorId());
        } catch (SQLException | BookSelectException e) {
            throw new BookCreateException();
        }
    }

    public void deleteBookById(int id) throws BookDeleteException {
        List<?> param = prepareParam(id);
        try {
            executor.execUpdate(DELETE_BOOK_BY_ID, param);
        } catch (SQLException e) {
            throw new BookDeleteException();
        }
    }

    public void updateBook(Book book) throws BookUpdateException {
        List<?> param = prepareParam(
                book.getTitle(),
                book.getAuthorId(),
                book.getDescription(),
                book.getQuantity(),
                book.getId());
        try {
            executor.execUpdate(UPDATE_BOOK, param);
        } catch (SQLException e) {
            throw new BookUpdateException();
        }
    }

    private List<Book> execAndGetListBooks(String query, List<?> param) throws SQLException {
        return executor.execQuery(query, param, resultSet -> {
            List<Book> bookList = new ArrayList<>();
            while (resultSet.next()) {
                bookList.add(parseBookFromResult(resultSet));
            }
            return bookList;
        });
    }

    private Book execAndGetBook(String query, List<?> param) throws SQLException {
        return executor.execQuery(query, param, resultSet -> {
            if (!resultSet.next())
                return null;
            return parseBookFromResult(resultSet);
        });
    }

    private Book parseBookFromResult(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int authorId = resultSet.getInt("authorId");
        String description = resultSet.getString("description");
        int quantity = resultSet.getInt("quantity");
        return new Book(id, title, authorId, description, quantity);
    }

    private List<?> prepareParam(Object ... objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }
}
