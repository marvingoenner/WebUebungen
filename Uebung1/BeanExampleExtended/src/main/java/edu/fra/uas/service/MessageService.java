package edu.fra.uas.service;

import org.springframework.stereotype.Component;

//Annotation Component macht Klasse zu Bean für Spring Container, Bean-Klasse hat eindeutigen identischen Namen mit kleinem Anfangsbuchstaben
@Component
public class MessageService {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
