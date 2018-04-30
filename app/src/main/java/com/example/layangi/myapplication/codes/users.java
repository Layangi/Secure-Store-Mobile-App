package com.example.layangi.myapplication.codes;

/**
 * Created by Lakshika on 26/03/2018.
 */

public class users {
    private int uid;
    private String uName;
    private String uEmail;
    private String uPassword;
    private String uNic;
    private String uKey;

    public users() {

    }

    public users(int uid, String uName, String uEmail, String uPassword, String uNic, String uKey) {
        this.uid = uid;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
        this.uNic = uNic;
        this.uKey = uKey;
    }

    public users(String uName, String uEmail, String uPassword, String uNic, String uKey) {
        this.uName = uName;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
        this.uNic = uNic;
        this.uKey = uKey;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuNic() {
        return uNic;
    }

    public void setuNic(String uNic) {
        this.uNic = uNic;
    }

    public String getuKey() {
        return uKey;
    }

    public void setuKey(String uKey) {
        this.uKey = uKey;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}