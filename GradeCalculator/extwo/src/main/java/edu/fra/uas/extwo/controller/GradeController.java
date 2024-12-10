package edu.fra.uas.extwo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.extwo.service.GradeService;

//Segregation of Duties: Controller: Präsentationslogik und Benutzerinteraktion. Vermittler zwischen Anwendung und Benutzer
//Aufgaben hier: Verwaltet Benutzereingaben
@Component
public class GradeController {
    @Autowired
    private GradeService gradeService;

    public String putMessage(String message) {
        gradeService.setMessage(" put message: " + message);
        return gradeService.getMessage();
    }

    public void addGrade(String subject, Double grade){
        gradeService.addGrade(subject, grade);
    }

    public double calculateAverage(){
        return gradeService.calculateAverage();
    }

    public String printGrades(){
        StringBuilder sb = new StringBuilder();
        gradeService.getGrades().forEach((subject, grade) -> {
            sb.append(subject).append(": ").append(grade).append("\n");
        });
        return sb.toString();
    }

}
