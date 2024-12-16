package edu.fra.uas.exfourrest.model;

public class Student {
    private Long id;
    private String name;
    private String matrikelnummer;

    // Konstruktoren
    public Student() {}

    public Student(Long id, String name, String matrikelnummer) {
        this.id = id;
        this.name = name;
        this.matrikelnummer = matrikelnummer;
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

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    public void setMatrikelnummer(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }
}

