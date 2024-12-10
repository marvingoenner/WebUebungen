package edu.fra.uas.exone.model;

public class Grade {

    private String subject;
    private double grade;
    private int creditPoints;

    
    
    public Grade(String subject, double grade, int creditPoints) {
        this.subject = subject;
        this.grade = grade;
        this.creditPoints = creditPoints;
    }

    // Getter und Setter
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }

}

