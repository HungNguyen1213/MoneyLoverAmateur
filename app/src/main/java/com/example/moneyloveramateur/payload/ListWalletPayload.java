package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.Wallet;

import java.util.List;

public class ListWalletPayload {
    private boolean isSuccess;

    private List<Wallet> listWallet;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Wallet> getListWallet() {
        return listWallet;
    }

    public void setListWallet(List<Wallet> listWallet) {
        this.listWallet = listWallet;
    }

    public ListWalletPayload(boolean isSuccess, List<Wallet> listWallet) {
        this.isSuccess = isSuccess;
        this.listWallet = listWallet;
    }

    public ListWalletPayload() {
    }
}
