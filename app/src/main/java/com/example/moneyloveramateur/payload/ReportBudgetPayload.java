package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.ReportBudget;

import java.util.List;

public class ReportBudgetPayload {
    private boolean isSuccess;
    private List<ReportBudget> listReportBudget;

    public ReportBudgetPayload(boolean isSuccess, List<ReportBudget> listReportBudget) {
        this.isSuccess = isSuccess;
        this.listReportBudget = listReportBudget;
    }

    public ReportBudgetPayload() {
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<ReportBudget> getListReportBudget() {
        return listReportBudget;
    }

    public void setListReportBudget(List<ReportBudget> listReportBudget) {
        this.listReportBudget = listReportBudget;
    }
}
