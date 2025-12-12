package de.thws.calculator;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/blocker-test")
public class BlockerTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isChrome = req.getHeader("sec-ch-ua").contains("Google Chrome");

        if (!isChrome) {
            resp.setStatus(400);
            resp.getWriter().write("Only Google Chrome is supported!");
            return;
        }
        resp.getWriter().write("You are working with Chrome - great job!");
    }

}
