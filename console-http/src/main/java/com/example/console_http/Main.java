package com.example.console_http;

import com.google.gson.Gson;
import com.example.console_http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.reflect.TypeToken;


public class Main {
    private static final String LOGIN_URL = "http://localhost:8080/api/users/login";
    private static final String RECIPES_URL = "http://localhost:8080/api/recipes";
    private static String authToken = null; // Speichert das Token nach erfolgreichem Login
    private static String userId = null; //Speichert UserID nach erfolgreichem Login


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Benutzer nach Login-Daten fragen
        System.out.println("🔐 Willkommen! Bitte logge dich ein.");
        System.out.print("Benutzername: ");
        String username = scanner.nextLine();
        System.out.print("Passwort: ");
        String password = scanner.nextLine();

        // Login-Versuch
        boolean loginErfolgreich = login(username, password);
        if (!loginErfolgreich) {
            System.out.println("❌ Login fehlgeschlagen! Bitte überprüfe deine Eingaben.");
            scanner.close();
            return;
        }

        // Menü für weitere Aktionen
        while (true) {
            System.out.println("\n🌍 Menue:");
            System.out.println("1 - Alle Rezepte abrufen");
            System.out.println("2 - Beenden");
            System.out.print("Bitte eine Option wählen: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Buffer leeren

            switch (option) {
                case 1:
                    getRecipes();
                    break;
                case 2:
                    System.out.println("👋 Programm beendet.");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Ungültige Eingabe! Bitte erneut versuchen.");
            }
        }
    }

    /** 
     * Führt einen Login-Versuch durch und speichert das Token, falls erfolgreich.
     */
    private static boolean login(String username, String password) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(LOGIN_URL);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Accept", "*/*");
    
            // JSON-Body mit den Login-Daten
            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", username);
            credentials.put("password", password);
            String jsonBody = new Gson().toJson(credentials);
            request.setEntity(new StringEntity(jsonBody));
    
            // Anfrage senden
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
    
                if (statusCode == 200 || statusCode == 201) {
                    // Auth-Token speichern
                    authToken = response.getFirstHeader("Authorization").getValue();
                    userId = response.getFirstHeader("UserID").getValue();  // Hier UserID speichern
                    System.out.println("✅ Login erfolgreich!");
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
    
    /** 
     * Holt alle Rezepte vom Backend.
     */
    private static void getRecipes() {
    if (authToken == null || userId == null) {
        System.out.println("⚠ Du musst dich zuerst einloggen!");
        return;
    }

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        HttpGet request = new HttpGet(RECIPES_URL);

        // Auth-Token & UserID in den Header setzen
        request.setHeader("Authorization", authToken);
        request.setHeader("UserID", userId);
        request.setHeader("Accept", "application/json");

        // Anfrage senden
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            if (statusCode == 200) {
                System.out.println("\n📜 Rezepte:");
                
                // JSON zu Recipe-Liste umwandeln und formatierte Ausgabe
                List<Recipe> recipes = new Gson().fromJson(responseBody, new TypeToken<List<Recipe>>(){}.getType());
                for (Recipe recipe : recipes) {
                    System.out.println("🔹 ID: " + recipe.getId() + " | Name: " + recipe.getName());
                    System.out.println("   " + recipe.getDescription());
                    System.out.println("------------------------------------");
                }
            } else {
                System.out.println("❌ Fehler beim Abrufen der Rezepte! Statuscode: " + statusCode);
            }
        }
    } catch (Exception e) {
        System.err.println("Fehler beim Abrufen der Rezepte: " + e.getMessage());
    }
}

}

