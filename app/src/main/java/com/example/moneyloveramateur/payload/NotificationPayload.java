package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.Notification;

public class NotificationPayload {
    private boolean isSuccess;

    private Notification notification;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public NotificationPayload(boolean isSuccess, Notification notification) {
        this.isSuccess = isSuccess;
        this.notification = notification;
    }

    public NotificationPayload() {
    }
}
