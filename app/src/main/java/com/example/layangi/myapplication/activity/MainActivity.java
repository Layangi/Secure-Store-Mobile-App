package com.example.layangi.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layangi.myapplication.R;
import com.example.layangi.myapplication.codes.EncryptDecrypt;
import com.example.layangi.myapplication.helpers.DBHelpers;
import com.example.layangi.myapplication.codes.users;

public class MainActivity extends AppCompatActivity  {

    EditText txtName, txtPassword ;

    String nameOut, passwordOut;

    public users users ;

//    CheckBox checkB = (CheckBox) findViewById(R.id.RemMe);
//    SharedPreferences sharedpreferences = PreferenceManager  .getDefaultSharedPreferences(getApplicationContext());

    /*
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void saveCheckBoxState(boolean isChecked){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("key",isChecked);
        editor.commit();
    }

    public boolean getCheckBoxState(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return prefs.getBoolean("key", false);
    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        users = new users();

        final DBHelpers dbh = new DBHelpers(this);
        final EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

        final boolean valid = true;

        final Button LoginBt;
        LoginBt = (Button) findViewById(R.id.btnLogin);

        final Button LogoutBt;
        LogoutBt = (Button) findViewById(R.id.btnLogout);

        txtName = findViewById(R.id.txtName);
        txtPassword = findViewById(R.id.txtPassword);

        AppCompatTextView textViewLinkRegister;

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.linkSign);

        textViewLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });


        LogoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Logging Out...");
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();
                                finish();

                                Toast.makeText(getBaseContext(), "Successfully Logged Out !", Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();
                            }
                        }, 3000);

                //rememner me
                //   setSharedPreference();
            }
        });

        LoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    nameOut = txtName.getText().toString();

                    passwordOut = txtPassword.getText().toString();

                 final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                            R.style.AppTheme_Dark_Dialog);
                    if (valid == true) {
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Authenticating...");
                        progressDialog.show();
                    }
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {

                                    try {
                                        if ((dbh.checkLogin(nameOut, passwordOut) != -1 && valid == true)) {

                                            Log.w("login","id: "+dbh.checkLogin(nameOut, passwordOut));
                                            String id = dbh.checkLogin(nameOut, passwordOut).toString();
                                            Toast.makeText(getBaseContext(), "Successfully Logged In !", Toast.LENGTH_LONG).show();
                                            reset();
                                            startActivity(new Intent(MainActivity.this, MenuActivity.class).putExtra("id",id));

                                            progressDialog.dismiss();
                                        }

                                        if ((dbh.checkLogin(nameOut, passwordOut)) == -1) {
                                            Toast.makeText(getBaseContext(), "Login failed ! Username And Password does not Match !", Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                            reset();

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, 3000);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

    });
}


    public void reset(){
        txtName.setText(null);
        txtPassword.setText(null);
    }


}
