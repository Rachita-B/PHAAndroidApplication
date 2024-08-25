package com.example.allphaapplication;
//This class cxontains user attributes with a constructor and getters
public class UserLogin {
    private int id;
    private String username, email, gender;

    public UserLogin(int id, String username, String email, String gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
    }
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }




}