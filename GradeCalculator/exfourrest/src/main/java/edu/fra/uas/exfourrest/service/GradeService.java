package edu.fra.uas.exfourrest.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

//Segregation of Duties: Service: Business Logic; Kernaufgaben der Anwendung
//Aufgben hier: Datenverarbeitung
@Component
public class GradeService {

    private String message;

    private Map<String, Double> grades = new HashMap<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double calculateAverage(){
        if (grades.isEmpty()){
            return 0.0;
        }
        double sum = grades.values().stream().mapToDouble(Double::doubleValue).sum();
        return sum / grades.size();
    }

    public void addGrade(String fach, Double note){
        grades.put(fach, note);
    }

    public Map<String, Double> getGrades(){
        return grades;
    }

    @PostConstruct
    public void initData(){
        grades.put("Math", 1.0);
        grades.put("Physics", 2.7);
        grades.put("Chemistry", 2.3);
        System.out.println("Data initialized: "+grades);
    }
    @PreDestroy
    public void clearData(){
        grades.clear();
        System.out.println("Data cleared");
    }
}
