package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.Group;

import java.util.List;

public class ListGroupPayload {
    private boolean isSuccess;

    private List<Group> listGroup;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Group> getListGroup() {
        return listGroup;
    }

    public void setListGroup(List<Group> listGroup) {
        this.listGroup = listGroup;
    }

    public ListGroupPayload(boolean isSuccess, List<Group> listGroup) {
        this.isSuccess = isSuccess;
        this.listGroup = listGroup;
    }

    public ListGroupPayload() {
    }
}
