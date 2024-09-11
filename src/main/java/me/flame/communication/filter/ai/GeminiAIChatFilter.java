package me.flame.communication.filter.ai;

import me.flame.communication.filter.ChatFilter;



import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GeminiAIChatFilter implements ChatFilter {
    private static final String API_URL = "https://api.gemini.google.com/v1/generate";
    private static final String API_KEY = "your_api_key";
    private static final int REQUESTS_PER_MINUTE = 10;

    // Rate limiter to control the number of requests per minute
    private final Semaphore rateLimiter;
    private final HttpClient httpClient;

    public GeminiAIChatFilter() {
        httpClient = HttpClient.newHttpClient();

        // Semaphore allows limited number of requests per minute
        rateLimiter = new Semaphore(REQUESTS_PER_MINUTE);
    }

    public CompletableFuture<Integer> generateRating(String prompt) {
        CompletableFuture<Integer> rating = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try {
                // Acquire a permit from the rate limiter
                rateLimiter.acquire();

                // Prepare the request body
                String requestBody = String.format("{\"prompt\": \"%s\"}", prompt);

                // Create the HTTP request
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .header("Authorization", "Bearer " + API_KEY)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .timeout(Duration.ofSeconds(30))
                        .build();

                // Send the request and handle the response
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    rating.complete(Integer.parseInt(response.body()));
                } else {
                    rating.completeExceptionally(new IllegalStateException(
                            String.format("Something wrong happened when sending the AI prompt to Gemini. \nStatus code: %s. \nBody: %s", response.statusCode(), response.body())));
                }
            } catch (InterruptedException | java.io.IOException e) {
                e.printStackTrace();
            } finally {
                rateLimiter.release();  // Ensure permit is released after request
            }
        });
        return rating;
    }

    @Override
    public void prohibitWord(final String word) {

    }

    @Override
    public void allowWord(final String word) {

    }

    @Override
    public boolean isMessageBlocked(final String message) {
        return false;
    }

    @Override
    public boolean canUseToCensor() {
        return false;
    }
}
