package com.example.moneyloveramateur.model;

import java.io.Serializable;

public class Exchange implements Serializable {
    private Long id;

    private long cost;

    private String note;

    private String date;

    private Group group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Exchange(Long id, long cost, String note, String date, Group group) {
        this.id = id;
        this.cost = cost;
        this.note = note;
        this.date = date;
        this.group = group;
    }

    public Exchange() {
    }

    public Exchange(long cost, String note, String date, Group group) {
        this.cost = cost;
        this.note = note;
        this.date = date;
        this.group = group;
    }
}
