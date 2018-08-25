package dao;

import entity.User;
import exception.user.UserSelectException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO userDAO;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        testUser = new User("Вася", 22, "vasiok@mail.ru", "password", "");
        userDAO = new UserDAO();
    }

    @Test
    public void getUserById() throws Exception {
        User newUser = userDAO.createUser(testUser);
        assertEquals(newUser , userDAO.getUserById(newUser.getId()));

        userDAO.deleteUserById(newUser.getId());
    }

    @Test
    public void getUserByIdNullResult() throws Exception {
        User newUser = userDAO.createUser(testUser);
        assertNull(userDAO.getUserById(newUser.getId() + 10));
        userDAO.deleteUserById(newUser.getId());
    }

    @Test
    public void getUserByEmail() throws Exception {
        User newUser = userDAO.createUser(testUser);
        assertEquals(newUser , userDAO.getUserByEmail(newUser.getEmail()));

        userDAO.deleteUserById(newUser.getId());
    }

    @Test
    public void getUserByEmailNullResult() throws Exception {
        assertNull(userDAO.getUserByEmail("qwerasdzxc"));
    }

    @Test
    public void createUser() throws Exception {
        User newUser = userDAO.createUser(testUser);
        assertNotNull(userDAO.getUserById(newUser.getId()));
        userDAO.deleteUserById(newUser.getId());
    }

    @Test
    public void deleteUserById() throws Exception {
        User newUser = userDAO.createUser(testUser);
        assertNotNull(userDAO.getUserById(newUser.getId()));
        userDAO.deleteUserById(newUser.getId());
        assertNull(userDAO.getUserById(newUser.getId()));
    }

    @Test
    public void updateUser() throws Exception {
        User newUser = userDAO.createUser(testUser);
        User secondUser = new User(newUser.getId(),"bla", 11, "some@gmail,com", "123", "");

        assertEquals(newUser, userDAO.getUserById(newUser.getId()));

        userDAO.updateUser(secondUser);
        assertEquals(secondUser, userDAO.getUserById(newUser.getId()));
    }
}