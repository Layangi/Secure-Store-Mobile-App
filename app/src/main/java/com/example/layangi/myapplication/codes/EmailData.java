package com.example.layangi.myapplication.codes;

/**
 * Created by Lakshika on 30/03/2018.
 */

public class EmailData {

    int storeId;
    String email;
    String emailPwd;
    int userId;

    public EmailData(int storeId, String email, String emailPwd, int userId) {
        this.storeId = storeId;
        this.email = email;
        this.emailPwd = emailPwd;
        this.userId = userId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailPwd() {
        return emailPwd;
    }

    public void setEmailPwd(String emailPwd) {
        this.emailPwd = emailPwd;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
