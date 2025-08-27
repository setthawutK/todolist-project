package com.example.WORKPLS.Entity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



public class Register {


    private String name;
    private String password;

    public Register(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
