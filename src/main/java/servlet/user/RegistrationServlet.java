package servlet.user;

import entity.User;
import exception.validator.InvalidDataException;
import exception.user.UserAlreadyExistException;
import exception.user.UserSQLExecException;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/Registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        User user = new User(
                req.getParameter("name"),
                Integer.parseInt(req.getParameter("age")),
                req.getParameter("email"),
                req.getParameter("password"),
                ""
        );
        try {
            userService.createUser(user);
            resp.sendRedirect("/singIn");
        } catch (InvalidDataException | UserAlreadyExistException | UserSQLExecException e) {
            e.printStackTrace();
        }

    }

}
