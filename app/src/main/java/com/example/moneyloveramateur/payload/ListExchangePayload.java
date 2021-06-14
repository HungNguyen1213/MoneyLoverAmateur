package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.Exchange;

import java.util.List;

public class ListExchangePayload {
    private boolean isSuccess;

    private List<Exchange> listExchange;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Exchange> getListExchange() {
        return listExchange;
    }

    public void setListExchange(List<Exchange> listExchange) {
        this.listExchange = listExchange;
    }

    public ListExchangePayload(boolean isSuccess, List<Exchange> listExchange) {
        this.isSuccess = isSuccess;
        this.listExchange = listExchange;
    }

    public ListExchangePayload() {
    }
}
