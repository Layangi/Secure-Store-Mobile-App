package com.example.layangi.myapplication.codes;

/**
 * Created by Lakshika on 23/03/2018.
 */

public class BankData {

    int storeId;
    String bank;
    String accNo;
    String pin;
    int userId;


    public BankData(int storeId, String bank, String accNo, String pin, int userId){

        this.bank = bank;
        this.storeId = storeId;
        this.accNo = accNo;
        this.pin = pin;
        this.userId = userId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }



}
