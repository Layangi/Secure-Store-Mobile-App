package com.example.layangi.myapplication.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.layangi.myapplication.codes.BankData;
import com.example.layangi.myapplication.codes.EmailData;
import com.example.layangi.myapplication.codes.EncryptDecrypt;
import com.example.layangi.myapplication.codes.users;

import java.util.ArrayList;
import java.util.List;


public class DBHelpers extends SQLiteOpenHelper{

    //initialize database name
    private static final String DB_NAME = "secure_store.db";

    //database columns
    public static final String KEY_ID = "id"; // each user has unique id
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private static final String TABLE_NAME = "users";



    public DBHelpers(Context context) {

        super(context.getApplicationContext(),DB_NAME,null,1);
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


            //create bank data table
            sqLiteDatabase.execSQL("CREATE TABLE bank_data(" +
                    "bStoreId Integer PRIMARY KEY autoincrement, " +
                    "bank text, " +
                    "accNo text, " +
                    "pin text, " +
                    "user Integer," +
                    "FOREIGN KEY (user) REFERENCES users (id))");

            //create email data table

            sqLiteDatabase.execSQL("CREATE TABLE email_data(" +
                    "eStoreId Integer PRIMARY KEY autoincrement, " +
                    "email text, " +
                    "emailPwd text, " +
                    "user Integer," +
                    "FOREIGN KEY (user) REFERENCES users (id))");


        //create users data table
        sqLiteDatabase.execSQL("CREATE TABLE users(" +
                "id Integer PRIMARY KEY autoincrement, " +
                "name text, " +
                "email text, " +
                "password text, " +
                "nic text, " +
                "eKey text) " );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void InsertUser(String name, String email, String pwd, String nic, String key)
    {
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("email",email);
        cv.put("password",pwd);
        cv.put("nic",nic);
        cv.put("eKey",key);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("users",null,cv);
        db.close();
    }
    public void InsertBankData(String bank, String accNo, String pin,Integer user)
    {
        ContentValues cv = new ContentValues();
        cv.put("bank",bank);
        cv.put("accNo",accNo);
        cv.put("pin",pin);
        cv.put("user",user);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("bank_data",null,cv);
        db.close();
    }

    public void InsertEmailData(String email, String emailPwd, Integer user)
    {
        ContentValues cv = new ContentValues();
        cv.put("email",email);
        cv.put("emailPwd",emailPwd);
        cv.put("user",user);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("email_data",null,cv);
        db.close();
    }

    public List<BankData> readAllBankData(int userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BankData> data = new ArrayList<>();
        Cursor cr = db.rawQuery("SELECT * FROM bank_data WHERE user = "+userId,null);
        if(cr.moveToFirst()){
            do {
                int idIdx = cr.getColumnIndex("bStoreId");
                int bankIdx = cr.getColumnIndex("bank");
                int accnoIdx = cr.getColumnIndex("accNo");
                int pinIdx = cr.getColumnIndex("pin");
                int userIdx = cr.getColumnIndex("user");

                Integer id = cr.getInt(idIdx);
                String bank = cr.getString(bankIdx);
                String accNo = cr.getString(accnoIdx);
                String pin = cr.getString(pinIdx);
                Integer user = cr.getInt(userIdx);
                data.add(new BankData(id,bank,accNo,pin,user));

            }
            while (cr.moveToNext());
        }
        cr.close();
        db.close();
        return data;
    }

    public List<EmailData> readAllEmailData(int userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<EmailData> data = new ArrayList<>();
        Cursor cr = db.rawQuery("SELECT * FROM email_data WHERE user = "+userId,null);
        if(cr.moveToFirst()){
            do {
                int idIdx = cr.getColumnIndex("eStoreId");
                int emailIdx = cr.getColumnIndex("email");
                int emailPwdIdx = cr.getColumnIndex("emailPwd");
                int userIdx = cr.getColumnIndex("user");

                Integer id = cr.getInt(idIdx);
                String email = cr.getString(emailIdx);
                String emailPwd = cr.getString(emailPwdIdx);
                Integer user = cr.getInt(userIdx);
                data.add(new EmailData(id,email,emailPwd,user));

            }
            while (cr.moveToNext());
        }
        cr.close();
        db.close();
        return data;
    }


    public int updateUserData(BankData data){

        ContentValues cv = new ContentValues();
        cv.put("bank",data.getBank());
        cv.put("accNo",data.getAccNo());
        cv.put("pin",data.getPin());

        SQLiteDatabase db = this.getWritableDatabase();
        return db.update("user_data",cv,"storeId ="+data.getStoreId()+" && user="+data.getUserId(),null);
    }
    public void deleteBankItem(int id,int user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM bank_data WHERE bStoreId = " + id + " and user = " + user);
        db.close();
    }

    public void deleteEmailItem(int id,int user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM email_data WHERE eStoreId = " + id + " and user = " + user);
        db.close();
    }

    public String getAlias(int userId){
        SQLiteDatabase db = this.getReadableDatabase();
        String alias = "";
        Log.w("get alias","query:"+db.rawQuery("SELECT * FROM users WHERE id = "+userId,null));
        Cursor cr = db.rawQuery("SELECT * FROM users ",null);
        if(cr.moveToFirst()){
            do {
                int id = cr.getInt(cr.getColumnIndex("id"));
                if(id == userId)
                    alias = cr.getString(cr.getColumnIndex("eKey"));
            }while(cr.moveToNext());
        }
        return alias;
    }
    public Integer checkLogin(String name, String password) throws Exception {

        EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users",null);
        if(cursor.moveToFirst()){
            do {
                int idIdx = cursor.getColumnIndex("id");
                int nameIdx = cursor.getColumnIndex("name");
                int pwdIdx = cursor.getColumnIndex("password");
                int keyIdx = cursor.getColumnIndex("eKey");

                Integer id = cursor.getInt(idIdx);
                String uname = cursor.getString(nameIdx);
                String pwd = cursor.getString(pwdIdx);
                String key = cursor.getString(keyIdx);
                if(name.equals(encryptDecrypt.decrypt(uname,key)) && password.equals(encryptDecrypt.decrypt(pwd,key))) {
                   return id;
                }

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return -1;
    }

       //When Login
    public  boolean checkUser(String email) {

        Log.w("ChechkUser","start1");
        SQLiteDatabase db = this.getReadableDatabase();
        //Log.w("DBB","Data:"+db.rawQuery("SELECT * FROM users  ",null));
       Cursor cr = db.rawQuery("SELECT email FROM users",null);
       if(cr.moveToFirst()){
           do {
               String emails = cr.getString(cr.getColumnIndex("email"));
               if(emails.equals(email))
               {
                   return true;
               }
               Log.w("email", "e:" + emails);

           }while(cr.moveToNext());
       }

        return false;
    }

    public List<users> readAllUserInfo(int id) {

        Log.w("DB", "starttt1");

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cr = db.rawQuery("SELECT * FROM users WHERE id = " + id, null);
        Log.w("DB","starttt 2");
        cr.moveToNext();

        int idIdx = cr.getColumnIndex("id");
        int nameIdx = cr.getColumnIndex("name");
        int emailIdx = cr.getColumnIndex("email");
        int passwordIdx = cr.getColumnIndex("password");
        int confirmIdx = cr.getColumnIndex("nic");
        int keyIdx = cr.getColumnIndex("eKey");

        List<users> data = new ArrayList<>();

        while (!cr.isAfterLast()) {

            Integer idU = cr.getInt(idIdx);
            String name = cr.getString(nameIdx);
            String email = cr.getString(emailIdx);
            String password = cr.getString(passwordIdx);
            String nic = cr.getString(confirmIdx);
            String eKey = cr.getString(keyIdx);

            data.add(new users(idU, name, email, password, nic, eKey));

            cr.moveToNext();
        }
        cr.close();
        db.close();
        return data;
    }

    public void updateUser(users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getuName());
        values.put(KEY_EMAIL, user.getuEmail());
        values.put(KEY_PASSWORD, user.getuPassword());

        // updating row
        db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getUid())});
        db.close();
    }

}
