package com.demo.myapps.HomeApp.Accounts;

public class AccountsModel {

    String accText;
    int accImg;

    public AccountsModel() {
    }

    public AccountsModel(String accText, int accImg) {
        this.accText = accText;
        this.accImg = accImg;
    }

    public String getAccText() {
        return accText;
    }

    public int getAccImg() {
        return accImg;
    }
}
