package com.demo.myapps.DataModels;

public class AccountDataModel {
    String accId;
    String accName;
    String accEmail;
    String accPassword;
    int accImg;
    String link;

    public AccountDataModel() {
    }

    public AccountDataModel(String accId,  String accEmail, String accPassword, String accName, int accImg, String link) {
        this.accId = accId;
        this.accName = accName;
        this.accEmail = accEmail;
        this.accPassword = accPassword;
        this.accImg = accImg;
        this.link = link;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccEmail() {
        return accEmail;
    }

    public void setAccEmail(String accEmail) {
        this.accEmail = accEmail;
    }

    public String getAccPassword() {
        return accPassword;
    }

    public void setAccPassword(String accPassword) {
        this.accPassword = accPassword;
    }

    public int getAccImg() {
        return accImg;
    }

    public void setAccImg(int accImg) {
        this.accImg = accImg;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
