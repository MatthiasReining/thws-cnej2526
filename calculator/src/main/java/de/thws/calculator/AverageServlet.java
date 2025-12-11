package de.thws.calculator;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/average")
public class AverageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] gradesArray = request.getParameterValues("grades");

        Double[] grades = new Double[gradesArray.length];

        for (int i = 0; i < gradesArray.length; i++) {
            try {
                grades[i] = Double.parseDouble(gradesArray[i]);
                if (grades[i] < 1.0 || grades[i] > 6.0) {
                    response.setStatus(400);

                    response.getWriter().write("Grade out of range (1.0-6.0): " + gradesArray[i]);
                    return;
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid grade input: " + gradesArray[i]);
                return;
            }
        }

        double average = Arrays.stream(grades).mapToDouble(Double::doubleValue).average().orElse(0.0);

        System.out.println("grades: " + Arrays.toString(gradesArray));

        response.setContentType("text/plain");
        response.getWriter().write("input grade: " + Arrays.toString(gradesArray) + "  \naverages" + average);
    }
}