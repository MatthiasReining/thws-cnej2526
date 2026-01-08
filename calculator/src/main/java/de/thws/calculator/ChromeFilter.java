package de.thws.calculator;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @WebFilter("/*")
public class ChromeFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String secChUaHeader = req.getHeader("sec-ch-ua");
        boolean isChrome = secChUaHeader != null && secChUaHeader.contains("Google Chrome");

        if (!isChrome) {
            res.setStatus(400);
            res.getWriter().write("Only Google Chrome is supported!");
            return;
        }

        super.doFilter(req, res, chain);
    }

}
