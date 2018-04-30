package com.example.layangi.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layangi.myapplication.R;
import com.example.layangi.myapplication.codes.EncryptDecrypt;
import com.example.layangi.myapplication.helpers.DBHelpers;

public class SignUpActivity extends AppCompatActivity {

    Button AfterSign;
    EditText signName, signPassword, signNic, signKey, signEmail;
    TextView member;

    String nameOut, emailOut, passwordOut, nicOut, keyOut;
    String key = "";

   // private DBHelpers dbHeer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       // getSupportActionBar().hide();

        signName = findViewById(R.id.signName);
        signEmail = findViewById(R.id.signEmail);
        signPassword = findViewById(R.id.signPassword);
        signNic = findViewById(R.id.signNic);
        signKey = findViewById(R.id.signKey);
        AfterSign = findViewById(R.id.btnSignUp);

        final DBHelpers dbh = new DBHelpers(this);
        final boolean valid = true;

        final EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

        AfterSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                key = signKey.getText().toString();
                Log.w("Sign up ","key:"+key);

                if(signName.getText().toString().isEmpty()){
                    signName.setError("Enter Username !");
                }
                else if(signEmail.getText().toString().isEmpty()){
                    signEmail.setError("Enter Email !");
                }
                else if(signPassword.getText().toString().isEmpty()){
                    signPassword.setError("Enter Password !");
                }
                else if(signPassword.length() < 4 || signPassword.length() > 10){
                    signPassword.setError("Between 4 and 10 Alphanumeric Characters !");
                }

                else if(signNic.getText().toString().isEmpty()){
                    signNic.setError("Enter Confirm Password !");
                }
                else if(!((signPassword.getText().toString()).equals(signNic.getText().toString()))){
                    signNic.setError("Password Does not Match !");
                }
                else if(signKey.getText().toString().isEmpty()){
                    signKey.setError("Enter Key !");
                }
//                if(dbHelper.checkUser(signEmail.getText().toString())){
//                    Snackbar.make(nestedScrollView, getString(R.string.error_mzg6), Snackbar.LENGTH_LONG).show();
//                }
                else {
                    try {
                        if (!signName.getText().toString().isEmpty()) {
                            nameOut = encryptDecrypt.encrypt(signName.getText().toString(), key);
                        } else {
                            nameOut = null;

                        }

                        if (!signEmail.getText().toString().isEmpty()) {
                            emailOut = encryptDecrypt.encrypt(signEmail.getText().toString(), key);
                        } else {
                            emailOut = null;

                        }

                        if (!signPassword.getText().toString().isEmpty()) {
                            passwordOut = encryptDecrypt.encrypt(signPassword.getText().toString(), key);
                        } else {
                            passwordOut = null;
                        }

                        if (!signNic.getText().toString().isEmpty()) {
                            nicOut = encryptDecrypt.encrypt(signNic.getText().toString(), key);
                        } else {
                            nicOut = null;
                        }

                        if (!signKey.getText().toString().isEmpty()) {
                            keyOut = signKey.getText().toString();
                        } else {
                            keyOut = null;
                        }

                        //users user = new users(nameOut, emailOut,  passwordOut,  nicOut, keyOut);

                        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                                R.style.AppTheme_Dark_Dialog);
                        if(valid == true) {
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Signing Up...");
                            progressDialog.show();
                        }
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        boolean validty =dbh.checkUser(emailOut);
                                        if(validty == false){
                                            dbh.InsertUser(nameOut, emailOut, passwordOut, nicOut, keyOut);
                                            Toast.makeText(getBaseContext(), "Successfully Signed Up ! !", Toast.LENGTH_LONG).show();
                                           // Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                                            reset();
                                            progressDialog.dismiss();
                                        }

                                        else{Toast.makeText(getBaseContext(), "Email Address Already Exists ! !", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, 3000);


//                        boolean validty =dbh.checkUser(emailOut);
//                        if(validty == false){
//                            dbh.InsertUser(nameOut, emailOut, passwordOut, nicOut, keyOut);
//                            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
//                            reset();
//                        }
//                        else{
//                            Snackbar.make(nestedScrollView, getString(R.string.error_mzg6), Snackbar.LENGTH_LONG).show();
//                        }

                        // Snack Bar to show success message that record saved successfully

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
//                else {
//                    Snackbar.make(nestedScrollView, getString(R.string.error_mzg), Snackbar.LENGTH_LONG).show();
//                }
            }
        });

        member = (TextView)findViewById(R.id.mem);

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });


    }

    public void reset(){
        signName.setText(null);
        signEmail.setText(null);
        signNic.setText(null);
        signPassword.setText(null);
        signKey.setText(null);

    }
}
