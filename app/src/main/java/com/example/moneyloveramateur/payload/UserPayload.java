package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.User;

public class UserPayload {

    private User user;

    private boolean isSuccess;

    public UserPayload() {
    }

    public UserPayload(User user, boolean isSuccess) {
        this.user = user;
        this.isSuccess = isSuccess;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
