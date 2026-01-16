package de.thws.courses.control;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GradeCalculator {

    @Retry(maxRetries = 3, delay = 5000)
    @Fallback(fallbackMethod = "calculateGradeFallback")
    public Double calculateGrade() {

        System.out.println("Calling external grade calculator service...");

        try {
            URI uriCalculator = new URI("http://localhost:8081/calculator/average?grades=2&grades=4");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uriCalculator)
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String plainText = response.body();

            String value = plainText.substring(plainText.indexOf("averages") + 8);

            Double result = Double.parseDouble(value);
            System.out.println("average: " + value);
            return result;

        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Double calculateGradeFallback() {
        // TODO call another server
        System.out.println("Fallback method called: returning default grade 3.0");
        return 1.0;
    }

}
