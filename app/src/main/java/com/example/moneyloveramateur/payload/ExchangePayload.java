package com.example.moneyloveramateur.payload;

import com.example.moneyloveramateur.model.Exchange;

public class ExchangePayload {
    private boolean isSuccess;

    private Exchange exchange;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public ExchangePayload() {
    }

    public ExchangePayload(boolean isSuccess, Exchange exchange) {
        this.isSuccess = isSuccess;
        this.exchange = exchange;
    }
}
