package edu.fra.uas.service;

import org.springframework.stereotype.Component;

@Component
public class MessageService {

    private int counter = 0;

    public void increment(){
        counter+=1;
    }

    public int getCounter(){
        return counter;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
