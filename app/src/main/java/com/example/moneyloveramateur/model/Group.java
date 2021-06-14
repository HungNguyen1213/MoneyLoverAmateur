package com.example.moneyloveramateur.model;

import java.io.Serializable;

public class Group implements Serializable {
    private Long id;

    private String name;

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
