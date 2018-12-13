package com.example.tunga.gb.model;

public class Person {
    private String id;
    private String name;
    private String username;
    private String telno;
    private String password;
    private String confirmPassword;
    private String role;

    public Person(String username,String telno,String id,String name, String password, String confirmPassword,String role) {
        this.username=username;
        this.id=id;
        this.name=name;
        this.telno = telno;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role=role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return telno;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.telno = phoneNumber;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }
}
