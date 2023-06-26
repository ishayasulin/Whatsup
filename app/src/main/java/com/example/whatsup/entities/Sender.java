package com.example.whatsup.entities;

import java.io.Serializable;

public class Sender implements Serializable {
    private String username;

    public String getUsername() {
        return username;
    }

    public Sender(String username) {
        this.username = username;
    }
}

