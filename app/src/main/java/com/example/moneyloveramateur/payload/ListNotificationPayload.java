package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.Notification;

import java.util.List;

public class ListNotificationPayload {
    private boolean isSuccess;

    private List<Notification> listNoti;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Notification> getListNoti() {
        return listNoti;
    }

    public void setListNoti(List<Notification> listNoti) {
        this.listNoti = listNoti;
    }

    public ListNotificationPayload(boolean isSuccess, List<Notification> listNoti) {
        this.isSuccess = isSuccess;
        this.listNoti = listNoti;
    }

    public ListNotificationPayload() {
    }
}
