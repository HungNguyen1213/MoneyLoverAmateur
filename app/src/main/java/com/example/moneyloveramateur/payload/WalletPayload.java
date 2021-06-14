package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.Wallet;

public class WalletPayload {
    private boolean isSuccess;

    private Wallet wallet;

    public WalletPayload() {
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public WalletPayload(boolean isSuccess, Wallet wallet) {
        this.isSuccess = isSuccess;
        this.wallet = wallet;
    }
}
