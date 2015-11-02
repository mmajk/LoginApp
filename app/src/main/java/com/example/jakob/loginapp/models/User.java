package com.example.jakob.loginapp.models;

/**
 * Created by jakob on 12-10-2015.
 */
public class User {

    private int Id;
    private String userName;
    private String email;
    private String password;
    private int loggenIn;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int isLoggenIn() {
        return loggenIn;
    }

    public void setLoggenIn(int loggenIn) {
        this.loggenIn = loggenIn;
    }
}
