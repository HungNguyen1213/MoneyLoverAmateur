package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.Budget;

public class BudgetPayload {
    private boolean isSuccess;

    private Budget budget;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public BudgetPayload(boolean isSuccess, Budget budget) {
        this.isSuccess = isSuccess;
        this.budget = budget;
    }

    public BudgetPayload() {
    }
}
