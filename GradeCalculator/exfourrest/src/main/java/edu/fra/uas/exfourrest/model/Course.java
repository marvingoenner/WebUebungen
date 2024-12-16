package edu.fra.uas.exfourrest.model;

public class Course {
    private Long id;
    private String courseName;
    private String lecturer;

    // Konstruktoren
    public Course() {}

    public Course(Long id, String courseName, String lecturer) {
        this.id = id;
        this.courseName = courseName;
        this.lecturer = lecturer;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
}
