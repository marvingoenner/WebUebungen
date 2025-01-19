package edu.fra.uas.Rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.fra.uas.Rest.service.GradeService;

import java.util.Map;

@RestController // Markiert die Klasse als REST-Controller
@RequestMapping("/api/grades") // Basis-URL für alle Endpunkte dieser Ressource
public class GradeRestController {

    @Autowired
    private GradeService gradeService;

    // GET: Alle Noten abrufen
    @GetMapping
    public Map<String, Double> getAllGrades() {
        return gradeService.getGrades(); // Gibt alle Noten als JSON zurück
    }

    // POST: Neue Note hinzufügen
    @PostMapping
    public String addGrade(@RequestParam String subject, @RequestParam Double grade) {
        gradeService.addGrade(subject, grade);
        return "Note für " + subject + " hinzugefügt.";
    }

    // DELETE: Eine Note löschen
    @DeleteMapping("/{subject}")
    public String deleteGrade(@PathVariable String subject) {
        gradeService.deleteGrade(subject);
        return "Note für " + subject + " gelöscht.";
    }
}

