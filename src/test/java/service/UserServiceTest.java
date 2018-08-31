package service;

import dao.UserDAO;
import entity.User;
import exception.validator.InvalidDataException;
import exception.user.UserAlreadyExistException;
import exception.user.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserDAO userDAO;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        userDAO = mock(UserDAO.class);
        userService = new UserService(userDAO);

        testUser = new User(3,"Вася", 22, "vasiok@mail.ru", "password", "");
    }

    @Test(expected = InvalidDataException.class)
    public void getUserByIdInvalidId() throws Exception {
        userService.getUserById(-1);
    }

    @Test
    public void getUserById() throws Exception {
        when(userDAO.getUserById(testUser.getId())).thenReturn(testUser);
        assertEquals(testUser, userService.getUserById(testUser.getId()));
    }

    @Test(expected = InvalidDataException.class)
    public void getUserByEmailInvalidEmail() throws Exception {
        userService.getUserByEmail("blablabla");
    }

    @Test
    public void getUserByEmail() throws Exception {
        userService.getUserByEmail(testUser.getEmail());
        verify(userDAO, times(1)).getUserByEmail(testUser.getEmail());
    }

    @Test(expected = InvalidDataException.class)
    public void createUserInvalidData() throws Exception {
        User newUser = new User("Sam", -1, "sam@mai.ru", "password", "");
        userService.createUser(newUser);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void createUserAlreadyExist() throws Exception {
        when(userDAO.getUserByEmail(testUser.getEmail())).thenReturn(testUser);
        userService.createUser(testUser);
    }

    @Test
    public void createUser() throws Exception {
        when(userDAO.getUserByEmail(testUser.getEmail())).thenReturn(null);
        userService.createUser(testUser);
        verify(userDAO, times(1)).createUser(testUser);
    }

    @Test(expected = InvalidDataException.class)
    public void deleteUserByIdInvalidId() throws Exception {
        userService.deleteUserById(-1);
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserByIdNotFound() throws Exception {
        when(userDAO.getUserById(anyInt())).thenReturn(null);
        userService.deleteUserById(testUser.getId());
    }

    @Test
    public void deleteUserById() throws Exception {
        when(userDAO.getUserById(testUser.getId())).thenReturn(testUser);
        userService.deleteUserById(testUser.getId());
        verify(userDAO, times(1)).deleteUserById(testUser.getId());
    }

    @Test(expected = InvalidDataException.class)
    public void updateUserInvalidData() throws Exception {
        User newUser = new User("Sam", -1, "sam@mai.ru", "password", "");
        userService.updateUser(newUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateUserNotFound() throws Exception {
        when(userDAO.getUserById(anyInt())).thenReturn(null);
        userService.updateUser(testUser);
    }

    @Test
    public void updateUser() throws Exception {
        when(userDAO.getUserById(testUser.getId())).thenReturn(testUser);
        userService.updateUser(testUser);
        verify(userDAO, times(1)).updateUser(testUser);
    }
}