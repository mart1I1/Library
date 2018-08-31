package service;

import dao.UserDAO;
import entity.User;
import exception.validator.InvalidDataException;
import exception.user.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import validator.Validator;

public class UserService {

    private final static Logger logger = LogManager.getLogger(UserService.class.getName());

    public UserService() {
        this.userDAO = new UserDAO();
    }

    UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private UserDAO userDAO;

    public User getUserById(int id) throws UserSQLExecException, InvalidDataException {
        try {
            Validator.validId(id);
            return userDAO.getUserById(id);
        } catch (InvalidDataException | UserSQLExecException e) {
            logger.error(id, e);
            throw e;
        }
    }

    public User getUserByEmail(String email) throws InvalidDataException, UserSQLExecException {
        try {
            Validator.validEmail(email);
            return userDAO.getUserByEmail(email);
        } catch (InvalidDataException | UserSQLExecException e) {
            logger.error(email, e);
            throw e;
        }
    }

    public User createUser(User user) throws InvalidDataException, UserAlreadyExistException, UserSQLExecException {
        try {
            Validator.validUser(user);
            if (getUserByEmail(user.getEmail()) != null)
                throw new UserAlreadyExistException();
            return userDAO.createUser(user);
        } catch (InvalidDataException | UserAlreadyExistException | UserSQLExecException e) {
            logger.error(user, e);
            throw e;
        }
    }

    public void deleteUserById(int id) throws InvalidDataException, UserNotFoundException, UserSQLExecException {
        try {
            Validator.validId(id);
            if (getUserById(id) == null)
                throw new UserNotFoundException();
            userDAO.deleteUserById(id);
        } catch (InvalidDataException | UserNotFoundException | UserSQLExecException e) {
            logger.error(id, e);
            throw e;
        }
    }

    public void updateUser(User user) throws InvalidDataException, UserNotFoundException, UserSQLExecException {
        try {
            Validator.validUser(user);
            if (getUserById(user.getId()) == null)
                throw new UserNotFoundException();
            userDAO.updateUser(user);
        } catch (InvalidDataException | UserNotFoundException | UserSQLExecException e) {
            logger.error(user, e);
            throw e;
        }
    }

}
