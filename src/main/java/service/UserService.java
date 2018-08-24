package service;

import dao.UserDAO;
import entity.User;

public class UserService {

    public UserService() {
        this.userDAO = new UserDAO();
    }

    private UserDAO userDAO;

    public User getUserByName(String name) {
        return userDAO.getUserByName(name);
    }

}
