package com.example.lenovo.glassbookingapp.model;

public class Help {

    String email;


     public Help(){

     }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Help{" +
                "email='" + email + '\'' +
                '}';
    }
}
