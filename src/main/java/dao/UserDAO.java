package dao;

import database.executor.Executor;
import entity.Book;
import entity.User;
import exception.user.UserCreateException;
import exception.user.UserDeleteException;
import exception.user.UserSelectException;
import exception.user.UserUpdateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAO.class.getName());
    private final static String GET_USER_BY_ID = "SELECT * FROM User where id = ?";
    private final static String GET_USER_BY_EMAIL = "SELECT * FROM User where email = ?";

    private final static String CREATE_USER = "INSERT INTO User(name, age, email, password, image)" +
            "VALUES(?, ?, ?, ?, ?)";
    private final static String DELETE_USER_BY_ID = "DELETE FROM User where id = ?";
    private final static String UPDATE_USER = "UPDATE User " +
            "SET name = ?, age = ?, email = ?, password = ?, image = ? WHERE id = ?";


    private Executor executor;

    public UserDAO() {
        this.executor = new Executor();
    }

    public User getUserById(int id) throws UserSelectException {
        List<?> param = prepareParam(id);
        try {
            return execAndGetUser(GET_USER_BY_ID, param);
        } catch (SQLException e) {
            logger.error("getUserById: " + e.getSQLState());
            throw new UserSelectException();
        }
    }

    public User getUserByEmail(String email) throws UserSelectException {
        List<?> param = prepareParam(email);
        try {
            return execAndGetUser(GET_USER_BY_EMAIL, param);
        } catch (SQLException e) {
            logger.error("getUserById: " + e.getSQLState());
            throw new UserSelectException();
        }
    }

    public User createUser(User user) throws UserCreateException {
        List<?> param = prepareParam(
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getImage()
        );
        try {
            executor.execUpdate(CREATE_USER, param);
            return getUserByEmail(user.getEmail());
        } catch (SQLException | UserSelectException e) {
            throw new UserCreateException();
        }
    }

    public void deleteUserById(int id) throws UserDeleteException {
        List<?> param = prepareParam(id);
        try {
            executor.execUpdate(DELETE_USER_BY_ID, param);
        } catch (SQLException e) {
            throw new UserDeleteException();
        }
    }

    public void updateUser(User user) throws UserUpdateException {
        List<?> param = prepareParam(
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getImage(),
                user.getId()
        );
        try {
            executor.execUpdate(UPDATE_USER, param);
        } catch (SQLException e) {
            throw new UserUpdateException();
        }
    }

    private User execAndGetUser(String query, List<?> param) throws SQLException {
        return executor.execQuery(query, param, resultSet -> {
            if (!resultSet.next())
                return null;
            return parseUserFromResult(resultSet);
        });
    }

    private User parseUserFromResult(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String image = resultSet.getString("image");
        return new User(id, name, age, email, password, image);
    }

    private List<?> prepareParam(Object ... objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }
}
