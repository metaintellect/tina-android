package com.metaintellect.Tina.models;

public class Account {

    private int id;
    private String fullName;
    private String cashRegister;
    private int cashRegisterId;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCashRegister() {
        return cashRegister;
    }

    public void setCashRegister(String cashRegister) {
        this.cashRegister = cashRegister;
    }

    public int getCashRegisterId() {
        return cashRegisterId;
    }

    public void setCashRegisterId(int cashRegisterId) {
        this.cashRegisterId = cashRegisterId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
