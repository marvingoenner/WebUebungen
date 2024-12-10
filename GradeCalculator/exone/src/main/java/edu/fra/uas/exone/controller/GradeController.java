package edu.fra.uas.exone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.exone.model.Grade;
import edu.fra.uas.exone.service.GradeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping
    public void addGrade(@RequestBody Grade grade){
        gradeService.addGrade(grade);
    }

    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    @DeleteMapping("/{subject}")
    public void deleteGrade(@PathVariable String subject) {
        gradeService.deleteGrade(subject);
    }

    @GetMapping("/average")
    public double calculateAverage() {
        return gradeService.calculateAverage();
    }

    @DeleteMapping("/reset")
    public void resetGrades() {
        gradeService.resetGrades();
    }

}

