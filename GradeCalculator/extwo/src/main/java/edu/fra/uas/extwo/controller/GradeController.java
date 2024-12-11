package edu.fra.uas.extwo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.fra.uas.extwo.service.GradeService;

//Segregation of Duties: Controller: Präsentationslogik und Benutzerinteraktion
//Aufgaben hier: Verwaltet Benutzereingaben und verbindet diese mit der Geschäftslogik
@Controller
public class GradeController {
    @Autowired
    private GradeService gradeService;

     // Zeigt die Notenübersicht im Browser an
     @GetMapping("/grades")
     public String showGrades(Model model) {
         model.addAttribute("grades", gradeService.getGrades());
         model.addAttribute("average", gradeService.calculateAverage());
         return "grades"; // Verweis auf die HTML-Datei grades.html
     }
 
     // Verarbeitet die Benutzereingabe über das Formular
     @PostMapping("/addGrade")
     public String addGrade(@RequestParam String subject, @RequestParam Double grade) {
         gradeService.addGrade(subject, grade);
         return "redirect:/grades"; // Nach der Eingabe wird die Übersicht erneut angezeigt
     }

    public String putMessage(String message) {
        gradeService.setMessage(" put message: " + message);
        return gradeService.getMessage();
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
