package dao;

import database.executor.Executor;
import entity.Author;
import exception.author.AuthorCreateException;
import exception.author.AuthorDeleteException;
import exception.author.AuthorSelectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AuthorDAO {

    private static final Logger logger = LogManager.getLogger(AuthorDAO.class.getName());

    private final static String GET_AUTHOR_BY_ID = "SELECT * FROM Author where id = ?";
    private final static String GET_AUTHOR_BY_NAME_N_BIRTHDAY = "SELECT * FROM Author where name = ? and birthday = ?";

    private final static String CREATE_AUTHOR = "INSERT INTO Author (name, birthday) " +
            "VALUES(?, ?)";
    private final static String DELETE_AUTHOR = "DELETE FROM Author where id = ?";

    private Executor executor;

    public AuthorDAO() {
        this.executor = new Executor();
    }

    public Author getAuthorByID(int id) throws AuthorSelectException {
        List<?> param = prepareParam(id);
        try {
            return execAndGetAuthor(GET_AUTHOR_BY_ID, param);
        } catch (SQLException e) {
            logger.error("getAuthorById: " + e.getSQLState());
            throw new AuthorSelectException("get by id");
        }
    }

    public Author getAuthorByNameNBirthday(String name, String birthday) throws AuthorSelectException {
        List<?> param = prepareParam(name, birthday);
        try {
            return execAndGetAuthor(GET_AUTHOR_BY_NAME_N_BIRTHDAY, param);
        } catch (SQLException e) {
            throw new AuthorSelectException("get by name and birthday");
        }
    }

    public Author createAuthor(Author author) throws AuthorCreateException {
        List<?> param = prepareParam(
                author.getName(),
                author.getBirthday()
        );
        try {
            executor.execUpdate(CREATE_AUTHOR, param);
            return getAuthorByNameNBirthday(author.getName(), author.getBirthday());
        } catch (SQLException | AuthorSelectException e) {
            throw new AuthorCreateException();
        }
    }

    public void deleteAuthorById(int id) throws AuthorDeleteException {
        List<?> param = prepareParam(id);
        try {
            executor.execUpdate(DELETE_AUTHOR, param);
        } catch (SQLException e) {
            throw new AuthorDeleteException();
        }
    }

    private Author execAndGetAuthor(String query, List<?> param) throws SQLException {
        return executor.execQuery(query, param, resultSet -> {
            if (!resultSet.next())
                return null;
            return parseAuthorFromResult(resultSet);
        });
    }

    private Author parseAuthorFromResult(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String birthday = resultSet.getString("birthday");
        return new Author(id, name, birthday);
    }

    private List<?> prepareParam(Object ... objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }
}
