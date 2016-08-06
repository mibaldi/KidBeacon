package com.mibaldi.kidbeacon.Data.Models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class User {

    public String id;
    public String email;
    public String name;

    public User() {
    }

    @Exclude
    public ArrayList<OwnGroup> groups = new ArrayList<>();


    public User(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
