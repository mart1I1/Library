package service;

import dao.UserDAO;
import entity.User;
import exception.InvalidDataException;
import exception.user.*;
import validator.Validator;

import javax.xml.bind.ValidationEvent;

public class UserService {

    public UserService() {
        this.userDAO = new UserDAO();
    }

    UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private UserDAO userDAO;

    public User getUserById(int id) throws UserSelectException, InvalidDataException {
        if (!Validator.validId(id))
            throw new InvalidDataException("bad id");
        return userDAO.getUserById(id);
    }

    public User getUserByEmail(String email) throws UserSelectException, InvalidDataException {
        if (!Validator.validEmail(email))
            throw new InvalidDataException("bad email");
        return userDAO.getUserByEmail(email);
    }

    public User createUser(User user) throws UserCreateException, InvalidDataException, UserSelectException, UserAlreadyExistException {
        if (!Validator.validEmail(user.getEmail()) || !Validator.validStringValues(user.getName())
            || !Validator.validPassword(user.getPassword()) || !Validator.validAge(user.getAge()))
            throw new InvalidDataException();
        if (getUserByEmail(user.getEmail()) != null)
            throw new UserAlreadyExistException();
        return userDAO.createUser(user);
    }

    public void deleteUserById(int id) throws UserDeleteException, InvalidDataException, UserSelectException, UserNotFoundException {
        if (!Validator.validId(id))
            throw new InvalidDataException("bad id");
        if (getUserById(id) == null)
            throw new UserNotFoundException();
        userDAO.deleteUserById(id);
    }

    public void updateUser(User user) throws UserUpdateException, InvalidDataException, UserSelectException, UserNotFoundException {
        if (!Validator.validEmail(user.getEmail()) && !Validator.validStringValues(user.getName())
                && !Validator.validPassword(user.getPassword()) && !Validator.validAge(user.getAge()))
            throw new InvalidDataException();
        if (getUserById(user.getId()) == null)
            throw new UserNotFoundException();
        userDAO.updateUser(user);
    }

}
