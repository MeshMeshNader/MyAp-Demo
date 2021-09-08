package com.demo.myapps.DataModels;

import java.util.ArrayList;

public class UserDataModel {
    String UserID;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String nationalID;
    String password;
    ArrayList<AccountDataModel> accounts;

    public UserDataModel() {
    }

    public UserDataModel(String userID, String firstName, String lastName, String email, String phoneNumber, String nationalID, String password) {
        UserID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.nationalID = nationalID;
        this.password = password;
        accounts = new ArrayList<>();
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<AccountDataModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<AccountDataModel> accounts) {
        this.accounts = accounts;
    }
}




