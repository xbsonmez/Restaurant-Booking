package com.example.tunga.gb.services;

public class MessageService {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageService{" +
                "message='" + message + '\'' +
                '}';
    }
}
