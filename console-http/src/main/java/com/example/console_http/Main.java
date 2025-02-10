package com.example.console_http;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.*;
import com.google.gson.reflect.TypeToken;

public class Main {
    private static final String LOGIN_URL = "http://localhost:8080/api/users/login";
    private static final String RECIPES_URL = "http://localhost:8080/api/recipes";
    private static final String INGREDIENTS_URL = "http://localhost:8080/api/ingredients";
    private static final String USERS_URL = "http://localhost:8080/api/restgraphql"; // URL für GraphQL User-Abfrage
    private static String authToken = null; // Speichert das Token nach erfolgreichem Login
    private static String userId = null; // Speichert UserID nach erfolgreichem Login

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
            System.out.println("\n🌍 Menü:");
            System.out.println("1 - Alle Rezepte abrufen");
            System.out.println("2 - Zutaten anzeigen");
            System.out.println("3 - User einsehen");
            System.out.println("4 - Rezeptfilter benutzen");
            System.out.println("5 - Die Beliebtesten Rezepte");
            System.out.println("6 - Beenden");
            System.out.print("Bitte eine Option wählen: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Scanner-Buffer leeren

            switch (option) {
                case 1:
                    getRecipes();
                    break;
                case 2:
                    getIngredients();
                    break;
                case 3:
                    viewUser();
                    break;
                case 4:
                    filterRecipes(); // NEUE METHODE AUFRUFEN
                    break;
                case 5:
                    filterMostFavoriteRecipes();
                    break;
                case 6:
                    System.out.println("👋 Programm beendet.");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Ungültige Eingabe! Bitte erneut versuchen.");
            }
        }

    }

    // Login
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
                    userId = response.getFirstHeader("UserID").getValue(); // Hier UserID speichern
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

    // Übergreifend
    // Benutzerabfrage ob Feld angezeigt werden soll
    private static boolean askUser(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(question + " (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("y");
    }

    // Meüpunkt 1 AlleRezepte aufrufen
    // Get REcipes
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
                    List<Recipe> recipes = new Gson().fromJson(responseBody, new TypeToken<List<Recipe>>() {
                    }.getType());
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

    // Meüpunkt 2Zutaten anzeigen
    // Get Ingredients
    private static void getIngredients() {
        if (authToken == null || userId == null) {
            System.out.println("⚠ Du musst dich zuerst einloggen!");
            return;
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(INGREDIENTS_URL);
            request.setHeader("Authorization", authToken);
            request.setHeader("UserID", userId);
            request.setHeader("Accept", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                if (statusCode == 200) {
                    System.out.println("\n🛒 Zutaten:");

                    List<Ingredient> ingredients = new Gson().fromJson(responseBody, new TypeToken<List<Ingredient>>() {
                    }.getType());
                    for (Ingredient ingredient : ingredients) {
                        System.out.println("🔹 ID: " + ingredient.getId() + " | Name: " + ingredient.getName());
                    }
                } else {
                    System.out.println("❌ Fehler beim Abrufen der Zutaten! Statuscode: " + statusCode);
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen der Zutaten: " + e.getMessage());
        }
    }

    // Meüpunkt 3 User Einsehen
    // User Einsicht
    private static void viewUser() {
        if (authToken == null || userId == null) {
            System.out.println("⚠ Du musst dich zuerst einloggen!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        List<String> fields = new ArrayList<>();

        System.out.println("👤 Welche Felder möchtest du sehen?");
        boolean showId = askUser("ID anzeigen?");
        boolean showRecipes = askUser("Rezepte anzeigen?");
        boolean showUsername = askUser("Benutzername anzeigen?");

        if (showId)
            fields.add("id");
        if (showRecipes)
            fields.add("recipes");
        if (showUsername)
            fields.add("username");

        if (fields.isEmpty()) {
            System.out.println("⚠ Keine Felder ausgewählt.");
            return;
        }

        // GraphQL Query erstellen
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("operation", "query");
        requestBody.addProperty("resource", "allUsers");
        requestBody.add("variables", new Gson().toJsonTree(new HashMap<>()));
        requestBody.add("fields", new Gson().toJsonTree(fields));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(USERS_URL);
            request.setHeader("Authorization", authToken);
            request.setHeader("UserID", userId);
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(new Gson().toJson(requestBody)));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                if (statusCode == 200) {
                    // JSON in Java-Objekte umwandeln
                    JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
                    List<User> users = new Gson().fromJson(
                            jsonResponse.getAsJsonObject("data").getAsJsonArray("allUsers"),
                            new TypeToken<List<User>>() {
                            }.getType());

                    // 📌 Tabellenkopf ausgeben
                    System.out.println("\n👥 User-Daten:");
                    System.out.println("-------------------------------------------------");
                    System.out.printf("| %-5s | %-15s | %-10s |\n", "ID", "Rezepte", "Benutzername");
                    System.out.println("-------------------------------------------------");

                    // 📌 User-Daten in die Tabelle schreiben
                    for (User user : users) {
                        String recipes = user.recipes != null ? user.recipes.toString() : "-";
                        String username = user.username != null ? user.username : "-";
                        System.out.printf("| %-5s | %-15s | %-10s |\n", user.id, recipes, username);
                    }

                    System.out.println("-------------------------------------------------");

                } else {
                    System.out.println("❌ Fehler beim Abrufen der User! Statuscode: " + statusCode);
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen der User: " + e.getMessage());
        }
    }

    // User Klasse
    private static class User {
        String id;
        List<Integer> recipes;
        String username;
    }

    // menüpunkt 4
    private static void filterRecipes() {
        if (authToken == null || userId == null) {
            System.out.println("⚠ Du musst dich zuerst einloggen!");
            return;
        }
    
        Scanner scanner = new Scanner(System.in);
        List<String> fields = new ArrayList<>();
        List<String> ingredientFields = new ArrayList<>();
    
        System.out.println("📌 Wähle aus, welche Rezept-Felder du sehen möchtest (y/n):");
    
        // Benutzer nach gewünschten Feldern fragen
        if (askUser("ID anzeigen?")) fields.add("id");
        if (askUser("Name anzeigen?")) fields.add("name");
        if (askUser("Beschreibung anzeigen?")) fields.add("description");
        if (askUser("Kalorien anzeigen?")) fields.add("kcal");
        if (askUser("Fett anzeigen?")) fields.add("fett");
        if (askUser("Kohlenhydrate anzeigen?")) fields.add("kohlenhydrate");
        if (askUser("Zucker anzeigen?")) fields.add("zucker");
        if (askUser("Eiweiß anzeigen?")) fields.add("eiweiss");
        if (askUser("Salz anzeigen?")) fields.add("salz");
    
        // Zutatenfelder auswählen
        boolean showIngredients = askUser("Zutaten anzeigen?");
        if (showIngredients) {
            System.out.println("📌 Wähle aus, welche Zutaten-Felder du sehen möchtest (y/n):");
            if (askUser("Zutaten: ID anzeigen?")) ingredientFields.add("id");
            if (askUser("Zutaten: Name anzeigen?")) ingredientFields.add("name");
            if (askUser("Zutaten: Eiweiß anzeigen?")) ingredientFields.add("eiweiss");
            if (askUser("Zutaten: Fett anzeigen?")) ingredientFields.add("fett");
            if (askUser("Zutaten: Kalorien anzeigen?")) ingredientFields.add("kcal");
            if (askUser("Zutaten: Kohlenhydrate anzeigen?")) ingredientFields.add("kohlenhydrate");
            if (askUser("Zutaten: Zucker anzeigen?")) ingredientFields.add("zucker");
            if (askUser("Zutaten: Salz anzeigen?")) ingredientFields.add("salz");
        }
    
        if (fields.isEmpty()) {
            System.out.println("⚠ Keine Felder ausgewählt.");
            return;
        }
    
        // GraphQL Query erstellen
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("operation", "query");
        requestBody.addProperty("resource", "allRecipes");
        requestBody.add("variables", new Gson().toJsonTree(new HashMap<>()));
    
        // Falls Zutatenfelder ausgewählt wurden, füge sie hinzu
        if (!ingredientFields.isEmpty()) {
            JsonObject ingredientsObject = new JsonObject();
            ingredientsObject.add("ingredients", new Gson().toJsonTree(ingredientFields));
            fields.add(new Gson().toJson(ingredientsObject));
        }
    
        requestBody.add("fields", new Gson().toJsonTree(fields));
    
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("http://localhost:8080/api/restgraphql");
            request.setHeader("Authorization", authToken);
            request.setHeader("UserID", userId);
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(new Gson().toJson(requestBody)));
    
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());
    
                if (statusCode == 200) {
                    JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
    
                    // Prüfen, ob die Server-Antwort korrekt ist
                    if (!jsonResponse.has("data") || !jsonResponse.getAsJsonObject("data").has("allRecipes")) {
                        System.out.println("❌ Fehler: Server-Antwort enthält nicht die erwartete Struktur.");
                        return;
                    }
    
                    JsonArray recipesArray = jsonResponse.getAsJsonObject("data").getAsJsonArray("allRecipes");
                    List<Recipe> recipes = new Gson().fromJson(recipesArray, new TypeToken<List<Recipe>>() {}.getType());
    
                    // Rezept-Tabelle ausgeben
                    printRecipeList(recipes, fields, showIngredients);
                } else {
                    System.out.println("❌ Fehler beim Abrufen der Rezepte! Statuscode: " + statusCode);
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen der Rezepte: " + e.getMessage());
        }
    }
    
    // Methode zur Ausgabe der Rezept-Tabelle basierend auf der Feld-Auswahl
    private static void printRecipeList(List<Recipe> recipes, List<String> fields, boolean showIngredients) {
        System.out.println("\n📜 Gefilterte Rezept-Daten:");
    
        for (Recipe recipe : recipes) {
            // Dynamisch alle Felder ausgeben
            if (fields.contains("id")) System.out.println("ID: " + recipe.getId());
            if (fields.contains("name")) System.out.println("Name: " + recipe.getName());
            if (fields.contains("description")) System.out.println("Beschreibung: " + recipe.getDescription());
            if (fields.contains("kcal")) System.out.println("Kalorien: " + recipe.getKcal());
            if (fields.contains("fett")) System.out.println("Fett: " + recipe.getFett());
            if (fields.contains("kohlenhydrate")) System.out.println("Kohlenhydrate: " + recipe.getKohlenhydrate());
            if (fields.contains("zucker")) System.out.println("Zucker: " + recipe.getZucker());
            if (fields.contains("eiweiss")) System.out.println("Eiweiß: " + recipe.getEiweiss());
            if (fields.contains("salz")) System.out.println("Salz: " + recipe.getSalz());
    
            System.out.println(); // Leerzeile für bessere Lesbarkeit
    
            // Zutaten anzeigen, falls sie ausgewählt wurden
            if (showIngredients && recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()) {
                System.out.println("🛒 Zutaten:");
                for (Ingredient ingredient : recipe.getIngredients()) {
                    System.out.println("- " + ingredient.getName());
                }
                System.out.println();
            }
    
            System.out.println("------------------------------------");
        }
    }
    
    //Menüpunkt 5
    private static void filterMostFavoriteRecipes() {
        if (authToken == null || userId == null) {
            System.out.println("⚠ Du musst dich zuerst einloggen!");
            return;
        }
    
        Scanner scanner = new Scanner(System.in);
        List<String> fields = new ArrayList<>();
    
        // Benutzer gibt die Anzahl der beliebtesten Rezepte ein
        System.out.print("🔢 Wie viele der beliebtesten Rezepte möchtest du sehen? ");
        int amount = scanner.nextInt();
        scanner.nextLine(); // Scanner-Buffer leeren
    
        System.out.println("📌 Wähle aus, welche Rezept-Felder du sehen möchtest (y/n):");
    
        // Benutzer nach gewünschten Feldern fragen
        if (askUser("ID anzeigen?")) fields.add("id");
        if (askUser("Name anzeigen?")) fields.add("name");
        if (askUser("Beschreibung anzeigen?")) fields.add("description");
        if (askUser("Kalorien anzeigen?")) fields.add("kcal");
        if (askUser("Fett anzeigen?")) fields.add("fett");
        if (askUser("Kohlenhydrate anzeigen?")) fields.add("kohlenhydrate");
        if (askUser("Zucker anzeigen?")) fields.add("zucker");
        if (askUser("Eiweiß anzeigen?")) fields.add("eiweiss");
        if (askUser("Salz anzeigen?")) fields.add("salz");
    
        if (fields.isEmpty()) {
            System.out.println("⚠ Keine Felder ausgewählt.");
            return;
        }
    
        // GraphQL Query erstellen
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("operation", "query");
        requestBody.addProperty("resource", "getMostFavoriteRecipes");
    
        // Anzahl der Rezepte als Variable setzen
        JsonObject variables = new JsonObject();
        variables.addProperty("amount", amount);
        requestBody.add("variables", variables);
    
        requestBody.add("fields", new Gson().toJsonTree(fields));
    
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("http://localhost:8080/api/restgraphql");
            request.setHeader("Authorization", authToken);
            request.setHeader("UserID", userId);
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(new Gson().toJson(requestBody)));
    
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());
    
                if (statusCode == 200) {
                    JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
    
                    // Prüfen, ob die Server-Antwort korrekt ist
                    if (!jsonResponse.has("data") || !jsonResponse.getAsJsonObject("data").has("getMostFavoriteRecipes")) {
                        System.out.println("❌ Fehler: Server-Antwort enthält nicht die erwartete Struktur.");
                        return;
                    }
    
                    JsonArray recipesArray = jsonResponse.getAsJsonObject("data").getAsJsonArray("getMostFavoriteRecipes");
                    List<Recipe> recipes = new Gson().fromJson(recipesArray, new TypeToken<List<Recipe>>() {}.getType());
    
                    // Rezept-Tabelle ausgeben
                    printRecipeList(recipes, fields, false);
                } else {
                    System.out.println("❌ Fehler beim Abrufen der beliebtesten Rezepte! Statuscode: " + statusCode);
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen der beliebtesten Rezepte: " + e.getMessage());
        }
    }
    
}
