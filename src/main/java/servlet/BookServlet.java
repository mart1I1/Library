package servlet;

import entity.Author;
import entity.Book;
import service.AuthorService;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        BookService bookService = new BookService();
//        AuthorService authorService = new AuthorService();
//        int id = Integer.parseInt(req.getParameter("bookId"));
//        Book book = bookService.getBookById(id);
//        if (book != null) {
//            Author author = authorService.getAuthorById(book.getAuthorId());
//            req.setAttribute("book", book);
//            req.setAttribute("author", author);
//            req.getRequestDispatcher("/Book.jsp").forward(req, resp);
//        }
    }
}
