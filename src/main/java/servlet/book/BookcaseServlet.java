package servlet.book;

import entity.User;
import exception.book.BookSQLExecException;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/bookcase")
public class BookcaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userObject");
        if (user != null)
            req.setAttribute("userName", user.getName());

        BookService bookService = new BookService();
        try {
            req.setAttribute("books", bookService.getExistBooks());
        }  catch (BookSQLExecException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/Bookcase.jsp").forward(req, resp);
    }
}
