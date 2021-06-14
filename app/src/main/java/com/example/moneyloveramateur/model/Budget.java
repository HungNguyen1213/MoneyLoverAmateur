package com.example.moneyloveramateur.model;

import java.io.Serializable;

public class Budget implements Serializable {
    private Long id;

    private long balance;

    private Group group;

    private String createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Budget(Long id, long balance, Group group, String createDate) {
        this.id = id;
        this.balance = balance;
        this.group = group;
        this.createDate = createDate;
    }

    public Budget(long balance, Group group, String createDate) {
        this.balance = balance;
        this.group = group;
        this.createDate = createDate;
    }

    public Budget() {
    }
}
