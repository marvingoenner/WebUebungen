package edu.fra.uas.Rest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

//Segregation of Duties: Service: Business Logic; Kernaufgaben der Anwendung
//Aufgben hier: Datenverarbeitung
@Component
public class GradeService {

    private String message;

    private Map<String, Double> grades = new HashMap<>();

    private List<String> history = new ArrayList<>();

    public void logChange(String message) {
        history.add(LocalDateTime.now() + ": " + message);
    }

    public List<String> getHistory() {
        return history;
    }
    

    public String getMessage() {
        return message;
    }

    // Beste Note
    public Map.Entry<String, Double> getBestGrade() {
        return grades.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);
    }

    // Schlechteste Note
    public Map.Entry<String, Double> getWorstGrade() {
        return grades.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
    }

    public Map<Integer, Long> getGradeDistribution() {
        // Gruppiere die Noten nach ganzzahligen Werten und zähle die Häufigkeit
        return grades.values().stream()
                .collect(Collectors.groupingBy(
                        grade -> (int) Math.floor(grade), // Rundung auf die nächste ganze Zahl
                        Collectors.counting() // Anzahl der Noten in dieser Kategorie
                ));
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double calculateAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = grades.values().stream().mapToDouble(Double::doubleValue).sum();
        return sum / grades.size();
    }

    public void addGrade(String subject, Double grade) {
        grades.put(subject, grade);
        logChange("Grade added: " + subject + " = " + grade);
    }

    public Map<String, Double> getGrades() {
        return grades;
    }

    public void undoLastChange() {
        if (!history.isEmpty()) {
            String lastChange = history.remove(history.size() - 1);
            System.out.println("Undoing change: " + lastChange);
    
            // Beispiel: Verarbeite die Historiennachricht
            if (lastChange.contains("Grade added")) {
                String subject = lastChange.split(":")[2].split(" = ")[0].trim();
                grades.remove(subject);
            } else if (lastChange.contains("Grade removed")) {
                // Falls du entfernte Noten speichern willst, um sie zurückzusetzen
                // Dies erfordert zusätzliche Logik, z. B. Speicherung der entfernten Note in der Historie
                System.out.println("Restoring removed grade (nicht implementiert)");
            }
        }
    }
    

    public void deleteGrade(String subject) {
        if (grades.containsKey(subject)) {
            grades.remove(subject);
            logChange("Grade removed: " + subject);
        } else {
            logChange("Attempted to remove non-existent grade: " + subject);
        }
    }

    @PostConstruct
    public void initData() {
        grades.put("Math", 1.0);
        grades.put("Physics", 2.7);
        grades.put("Chemistry", 2.3);
        System.out.println("Data initialized: " + grades);
    }

    @PreDestroy
    public void clearData() {
        grades.clear();
        System.out.println("Data cleared");
    }
}

