package com.example.lenovo.glassbookingapp.Services;

public class MessageService {

    public String message;


    public MessageService() {

    }

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
