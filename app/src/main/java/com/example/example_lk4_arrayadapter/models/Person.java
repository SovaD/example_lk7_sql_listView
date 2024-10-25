package com.example.example_lk4_arrayadapter.models;

import java.io.Serializable;

public class Person implements Serializable {
    public int id;
    public String name;
    public String email;
    public String image;

    public Person(int id, String name, String email, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public Person(String name, String email, String image) {
        this.name = name;
        this.email = email;
        this.image = image;
    }
}
