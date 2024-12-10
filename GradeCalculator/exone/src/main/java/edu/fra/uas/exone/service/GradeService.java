package edu.fra.uas.exone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.fra.uas.exone.model.Grade;

@Service //Klasse als Service Komponente, technisch wie @component
public class GradeService {
    private List<Grade> grades = new ArrayList<>();

    public void addGrade(Grade grade){
        grades.add(grade);
    }

    public void deleteGrade(String subject) {
        grades.removeIf(g -> g.getSubject().equalsIgnoreCase(subject));
    }

    public double calculateAverage() {
        return grades.stream()
                .mapToDouble(Grade::getGrade)
                .average()
                .orElse(0.0);
    }

    public void resetGrades() {
        grades.clear();
    }

    public List<Grade> getAllGrades() {
        return grades;
    }
    
}

