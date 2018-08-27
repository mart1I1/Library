package servlet.book;

import exception.book.BookSelectException;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookcase")
public class BookcaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookService bookService = new BookService();
        try {
            req.setAttribute("books", bookService.getExistBooks());
        } catch (BookSelectException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/Bookcase.jsp").forward(req, resp);
    }
}
