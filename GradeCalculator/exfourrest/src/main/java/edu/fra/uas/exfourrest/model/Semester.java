package edu.fra.uas.exfourrest.model;

public class Semester {
    private Long id;
    private String name;
    private String timePeriod;

    // Konstruktoren
    public Semester() {}

    public Semester(Long id, String name, String timePeriod) {
        this.id = id;
        this.name = name;
        this.timePeriod = timePeriod;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }
}
