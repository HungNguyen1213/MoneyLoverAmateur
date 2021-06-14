package com.example.moneyloveramateur.model;

import java.io.Serializable;
import java.util.List;

public class ListExchangeOnday implements Serializable {
    private List<Exchange> exchangeList;
    private String day, month, year;
    private long amount;

    public ListExchangeOnday() {
    }

    public ListExchangeOnday(List<Exchange> exchangeList, String day, String month, String year, long amount) {
        this.exchangeList = exchangeList;
        this.day = day;
        this.month = month;
        this.year = year;
        this.amount = amount;
    }

    public List<Exchange> getExchangeList() {
        return exchangeList;
    }

    public void setExchangeList(List<Exchange> exchangeList) {
        this.exchangeList = exchangeList;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
