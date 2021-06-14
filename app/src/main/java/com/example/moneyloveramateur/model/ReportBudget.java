package com.example.moneyloveramateur.model;

import java.io.Serializable;

public class ReportBudget implements Serializable {
    private Budget budget;
    private long totalAmount;

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ReportBudget(Budget budget, long totalAmount) {
        this.budget = budget;
        this.totalAmount = totalAmount;
    }

    public ReportBudget() {
    }
}
