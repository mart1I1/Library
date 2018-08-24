package dao;

import database.connection.ConnectionPool;
import database.executor.Executor;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAO.class.getName());
    private final static String GET_USER_BY_NAME = "SELECT * FROM User where name = ?";

    private Executor executor;

    public UserDAO() {
        this.executor = new Executor();
    }

    public User getUserByName(String name) {
        List<String> param = new ArrayList<>();
        param.add(name);
        try {
            return executor.execQuery(GET_USER_BY_NAME, param, resultSet -> {
                if (!resultSet.next())
                    return null;
                return new User(resultSet.getInt("id"), resultSet.getString("name"));
            });
        } catch (SQLException e) {
            logger.error("getUserByName: " + e.getSQLState());
        }
        return null;
    }
}
