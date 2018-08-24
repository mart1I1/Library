package dao;

import database.executor.Executor;
import entity.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    private static final Logger logger = LogManager.getLogger(AuthorDAO.class.getName());
    private final static String GET_AUTHOR_BY_ID = "SELECT * FROM author where id = ?";

    private Executor executor;

    public AuthorDAO() {
        this.executor = new Executor();
    }

    public Author getAuthorByID(int id) {
        List<Integer> param = new ArrayList<>();
        param.add(id);

        try {
            return executor.execQuery(GET_AUTHOR_BY_ID, param, resultSet -> {
                if (!resultSet.next())
                    return null;
                return new Author(resultSet.getInt("id"), resultSet.getString("name"));
            });
        } catch (SQLException e) {
            logger.error("getAuthorById: " + e.getSQLState());
        }
        return null;
    }
}
