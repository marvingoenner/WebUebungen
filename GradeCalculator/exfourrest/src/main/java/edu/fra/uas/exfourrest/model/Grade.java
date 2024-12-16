package edu.fra.uas.exfourrest.model;

public class Grade {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Double gradeValue;

    // Konstruktoren
    public Grade() {}

    public Grade(Long id, Long studentId, Long courseId, Double gradeValue) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.gradeValue = gradeValue;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(Double gradeValue) {
        this.gradeValue = gradeValue;
    }
}
