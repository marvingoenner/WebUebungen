package com.example.console_http;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.HashMap;
import java.util.Map;

public class LoginService {
    private static final String LOGIN_URL = "http://localhost:8080/api/users/login";
    private static String authToken = null; // Speichert das Token für spätere Requests
    private static String userId = null;

    public static boolean login(String username, String password) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // HTTP-POST-Request vorbereiten
            HttpPost request = new HttpPost(LOGIN_URL);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Accept", "*/*");

            // JSON-Body erstellen
            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", username);
            credentials.put("password", password);
            String jsonBody = new Gson().toJson(credentials);
            request.setEntity(new StringEntity(jsonBody));

            // Anfrage senden
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();

                // Falls erfolgreich (200 oder 201)
                if (statusCode == 200 || statusCode == 201) {
                    // Auth-Token aus dem Header extrahieren
                    authToken = response.getFirstHeader("Authorization").getValue();
                    userId = response.getFirstHeader("UserID").getValue();

                    System.out.println("✅ Login erfolgreich!");
                    System.out.println("Token: " + authToken);
                    System.out.println("UserID: " + userId);
                    return true;
                } else {
                    System.out.println("❌ Login fehlgeschlagen! Statuscode: " + statusCode);
                    System.out.println("Antwort: " + EntityUtils.toString(response.getEntity()));
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Login: " + e.getMessage());
            return false;
        }
    }

    // Getter für Token, um es in anderen Requests zu verwenden
    public static String getAuthToken() {
        return authToken;
    }

    public static String getUserId() {
        return userId;
    }
}
