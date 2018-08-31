package servlet.user;

import entity.User;
import exception.validator.InvalidDataException;
import exception.user.UserSQLExecException;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/singIn")
public class SingInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/SingIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserService userService = new UserService();

        String userEmail = req.getParameter("email");
        String userPassword = req.getParameter("password");

        try {
            User user = userService.getUserByEmail(userEmail);
            if (user != null && user.getPassword().equals(userPassword)) {
                session.setAttribute("userObject", user);
                resp.sendRedirect("/bookcase");
            }
        } catch (InvalidDataException | UserSQLExecException e) {
            e.printStackTrace();
        }
    }
}
