package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.Report;

import java.util.List;

public class ListReportPayload {
    private boolean isSuccess;
    private List<Report> listReport;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Report> getListReport() {
        return listReport;
    }

    public void setListReport(List<Report> listReport) {
        this.listReport = listReport;
    }

    public ListReportPayload(boolean isSuccess, List<Report> listReport) {
        this.isSuccess = isSuccess;
        this.listReport = listReport;
    }

    public ListReportPayload() {
    }
}
