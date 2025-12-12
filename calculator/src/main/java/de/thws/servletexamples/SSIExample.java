package de.thws.servletexamples;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ssi-example")
public class SSIExample extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().write("<html><body>\n" +
                "<h1>Server Side Include Example</h1>\n" +
                "<p>Below is the included content:</p>\n" +
                "<hr>\n");

        req.getRequestDispatcher("/average?grades=2&grades=4").include(req, resp);

        resp.getWriter().write(" <br><br><br><hr>   Footer & Time " + java.time.LocalDateTime.now() + "\n" +
                "<hr>\n" +
                "</body></html>\n");

    }

}
