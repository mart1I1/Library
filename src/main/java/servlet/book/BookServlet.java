package servlet.book;

import entity.Author;
import entity.Book;
import exception.validator.InvalidDataException;
import exception.author.AuthorSQLExecException;
import exception.book.BookSQLExecException;
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
        BookService bookService = new BookService();
        AuthorService authorService = new AuthorService();
        int id = Integer.parseInt(req.getParameter("bookId"));
        Book book = null;
        try {
            book = bookService.getBookById(id);
        } catch (InvalidDataException | BookSQLExecException e) {
            e.printStackTrace();
        }
        if (book != null) {
            Author author = null;
            try {
                author = authorService.getAuthorById(book.getAuthorId());
            } catch (InvalidDataException | AuthorSQLExecException e) {
                e.printStackTrace();
            }
            req.setAttribute("book", book);
            req.setAttribute("author", author);
            req.getRequestDispatcher("/Book.jsp").forward(req, resp);
        }
    }
}
