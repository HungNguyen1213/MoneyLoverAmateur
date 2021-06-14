package com.example.moneyloveramateur.model;

public class Report {
    private Long groupId;
    private String groupName;
    private String totalAmount;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Report() {
    }

    public Report(Long groupId, String groupName, String totalAmount) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.totalAmount = totalAmount;
    }
}
