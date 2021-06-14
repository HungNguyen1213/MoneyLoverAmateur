package com.example.moneyloveramateur.model;

import java.io.Serializable;

public class Notification implements Serializable {
    private Long id;

    private String content;

    private String date;

    public Notification(Long id, String content, String date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
