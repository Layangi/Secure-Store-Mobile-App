package com.example.layangi.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.layangi.myapplication.R;
import com.example.layangi.myapplication.codes.EncryptDecrypt;
import com.example.layangi.myapplication.helpers.DBHelpers;


public class DataStoreActivity extends AppCompatActivity {


    EditText ccn,pin,bank,emailAdd,emailPwd;
    Button save,view,viewemail;
    String ccnOutput,bankOutput,pinOutPut,emailOutput,emailPwdOutput;

    String key = "";
    boolean valid = true;
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_store);


        id = getIntent().getStringExtra("id");
        ccn = findViewById(R.id.ccn);
        bank = findViewById(R.id.bank);
        pin = findViewById(R.id.pin);
        emailAdd = findViewById(R.id.emailAdd);
        emailPwd = findViewById(R.id.emailPwd);

        save = findViewById(R.id.save);
        view = findViewById(R.id.view);
        viewemail = findViewById(R.id.viewemail);

        final DBHelpers dbHelper = new DBHelpers(this);

        final EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

        Log.w("data store","id:"+id);
        key = dbHelper.getAlias(Integer.parseInt(id));
        Log.w("key","key is:"+key);
        //key="123";

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (!ccn.getText().toString().isEmpty()) {
                        ccnOutput = encryptDecrypt.encrypt(ccn.getText().toString(), key);
                    } else {
                        ccnOutput = null;
                    }

                    if (!bank.getText().toString().isEmpty()) {
                        bankOutput = encryptDecrypt.encrypt(bank.getText().toString(), key);
                    } else {
                        bankOutput = null;
                    }

                    if (!pin.getText().toString().isEmpty()) {
                        pinOutPut = encryptDecrypt.encrypt(pin.getText().toString(), key);
                    } else {
                        pinOutPut = null;
                    }

                    if (!emailAdd.getText().toString().isEmpty()) {
                        if ( !android.util.Patterns.EMAIL_ADDRESS.matcher(emailAdd.getText().toString()).matches()) {
                            emailAdd.setError("enter a valid email address");
                            valid = false;

                        } else {
                            emailOutput = encryptDecrypt.encrypt(emailAdd.getText().toString(), key);
                        }

                    } else {
                        emailOutput = null;
                    }

                    if (!emailPwd.getText().toString().isEmpty()) {
                        emailPwdOutput = encryptDecrypt.encrypt(emailPwd.getText().toString(), key);
                    } else {
                        emailPwdOutput = null;
                    }

                    final ProgressDialog progressDialog = new ProgressDialog(DataStoreActivity.this,
                            R.style.AppTheme_Dark_Dialog);
                    if(valid == true) {
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Saving...");
                        progressDialog.show();
                    }
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    if (bankOutput != null) {
                                        dbHelper.InsertBankData(bankOutput, ccnOutput, pinOutPut, Integer.parseInt(id));
                                        resetBankFields();
                                    }
                                    if (emailPwdOutput != null && valid == true) {
                                        dbHelper.InsertEmailData(emailOutput, emailPwdOutput, Integer.parseInt(id));
                                        resetEmailFields();
                                    }
                                    progressDialog.dismiss();
                                }
                            }, 3000);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DataStoreActivity  .this, BankDataViewActivity.class).putExtra("id",id));

            }
        });
        viewemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DataStoreActivity  .this, EmailDataActivity.class).putExtra("id",id));

            }
        });
    }

    public void resetBankFields(){
        ccn.setText("");
        bank.setText("");
        pin.setText("");
    }

    public void resetEmailFields(){
        emailAdd.setText("");
        emailPwd.setText("");
    }


}
