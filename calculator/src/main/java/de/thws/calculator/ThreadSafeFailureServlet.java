package de.thws.calculator;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/thread")
public class ThreadSafeFailureServlet extends HttpServlet {

    // DO NOT DO THIS!!!!
    private long mysecret;

    public ThreadSafeFailureServlet() {
        System.out.println("bin im Konstruktor des ThreadSafeServlets");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        mysecret = System.currentTimeMillis();
        System.out.println("my secret is (init): " + mysecret);
        System.out.println("bin in doGet des threadsaveServlets");

        // heavy load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("my secret is: " + mysecret);

        response.setContentType("text/plain");
        response.getWriter().write("ThreadSafeServlet is running. (" + System.currentTimeMillis() + ")");
    }
}