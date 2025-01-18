package edu.fra.uas.exfourrest.controller;

import edu.fra.uas.exfourrest.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    // In-Memory-Datenstrukturen für alle Ressourcen
    private Map<Long, Student> students = new HashMap<>();
    private Map<Long, Course> courses = new HashMap<>();
    private Map<Long, Grade> grades = new HashMap<>();
    private Map<Long, Semester> semesters = new HashMap<>();

    // ----------------- STUDENTS -----------------

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return students.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new ArrayList<>(students.values()));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return students.containsKey(id)
                ? ResponseEntity.ok(students.get(id))
                : ResponseEntity.notFound().build();
    }

    private long nextStudentId=1;

    @PostMapping("/students")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        student.setId(nextStudentId++);
        students.put(student.getId(), student);
        return ResponseEntity.status(201).body("Student hinzugefügt.");
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (students.containsKey(id)) {
            student.setId(id);
            students.put(id, student);
            return ResponseEntity.ok("Student aktualisiert.");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        if (students.remove(id) != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ----------------- COURSES -----------------
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return courses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new ArrayList<>(courses.values()));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courses.containsKey(id)
                ? ResponseEntity.ok(courses.get(id))
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/courses")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        courses.put(course.getId(), course);
        return ResponseEntity.status(201).body("Course hinzugefügt.");
    }

    // ----------------- GRADES -----------------
    @GetMapping("/grades")
    public ResponseEntity<List<Grade>> getAllGrades() {
        return grades.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new ArrayList<>(grades.values()));
    }

    @PostMapping("/grades")
    public ResponseEntity<String> addGrade(@RequestBody Grade grade) {
        grades.put(grade.getId(), grade);
        return ResponseEntity.status(201).body("Grade hinzugefügt.");
    }

    @GetMapping("/grades/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Long id) {
        return grades.containsKey(id)
                ? ResponseEntity.ok(grades.get(id))
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/grades/{id}")
    public ResponseEntity<String> updateGrade(@PathVariable Long id, @RequestBody Grade grade) {
        if (grades.containsKey(id)) {
            grade.setId(id);
            grades.put(id, grade);
            return ResponseEntity.ok("Grade aktualisiert.");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/grades/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long id) {
        if (grades.remove(id) != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    // ----------------- SEMESTERS -----------------
    @GetMapping("/semesters")
    public ResponseEntity<List<Semester>> getAllSemesters() {
        return semesters.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new ArrayList<>(semesters.values()));
    }

    @PostMapping("/semesters")
    public ResponseEntity<String> addSemester(@RequestBody Semester semester) {
        semesters.put(semester.getId(), semester);
        return ResponseEntity.status(201).body("Semester hinzugefügt.");
    }

    @GetMapping("/semesters/{id}")
    public ResponseEntity<Semester> getSemesterById(@PathVariable Long id) {
        return semesters.containsKey(id)
                ? ResponseEntity.ok(semesters.get(id))
                : ResponseEntity.notFound().build();
    }
}
