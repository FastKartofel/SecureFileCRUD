package com.example.demo1.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;
    private String pass;
    private String role;

    private List<UserFile> userFiles = new ArrayList<>();

    public User(String login, String pass, String role){
        this.login = login;
        this.pass = pass;
        this.role = role;
    }

    public void addFile(UserFile userFile){
        this.userFiles.add(userFile);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public List<UserFile> getUserFiles() {
        return userFiles;
    }


    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", userFiles=" + userFiles +
                '}';
    }
}
