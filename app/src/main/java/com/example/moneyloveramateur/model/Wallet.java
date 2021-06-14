package com.example.moneyloveramateur.model;

import java.io.Serializable;

public class Wallet implements Serializable {
    private Long id;
    private long balance;
    private String name;

    public Wallet() {
    }

    public Wallet(Long id, long balance, String name) {
        this.id = id;
        this.balance = balance;
        this.name = name;
    }

    public Wallet(long balance, String name) {
        this.balance = balance;
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
