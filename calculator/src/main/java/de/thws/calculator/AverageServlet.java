package de.thws.calculator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
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

        double average = calculatedAverage(grades);

        System.out.println("grades: " + Arrays.toString(gradesArray));

        response.setContentType("text/plain");
        response.getWriter().write("input grade: " + Arrays.toString(gradesArray) + "  \naverages" + average);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String contentType = request.getContentType();
        String accept = request.getHeader("Accept");

        Double average = 0.0;
        switch (contentType) {
            case "application/json" -> average = processJSON(request, response);
            case "application/thws" -> average = processTHWS(request, response);
            default -> {
                response.getWriter().write("Unsupported Content-Type: " + contentType);
                response.setStatus(415);
                return;
            }
        }

        switch (accept) {
            case "application/json" -> {
                response.setContentType("application/json");
                response.getWriter().write("{\"average\": " + average + "}");
            }
            case "text/plain", "*/*" -> {
                response.setContentType("text/plain");
                response.getWriter().write("average: " + average);
            }
            default -> {
                response.getWriter().write("Unsupported Accept type: " + accept);
                response.setStatus(406);
                return;
            }
        }

    }

    /**
     * Process JSON input to calculate average grade.
     */
    protected Double processJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject content = Json.createReader(request.getReader()).readObject();

        JsonArray grades = content.getJsonArray("grades");
        // Convert JsonArray to Double[]
        var gradesArray = grades.stream().map(g -> g.toString()).map(g -> Double.parseDouble(g)).toArray(Double[]::new);

        return this.calculatedAverage(gradesArray);

    }

    /**
     * Process THWS format input to calculate average grade.
     * DON'T DO THIS IN REAL WORLD. Use text/csv
     */
    protected Double processTHWS(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        var gradesArray = Arrays.stream(body.split(",")).map(g -> Double.parseDouble(g)).toArray(Double[]::new);

        return this.calculatedAverage(gradesArray);

    }

    private Double calculatedAverage(Double[] grades) {

        return Arrays.stream(grades).mapToDouble(Double::doubleValue).average().orElse(0.0);

    }

}