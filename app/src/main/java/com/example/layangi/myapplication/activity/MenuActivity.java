package com.example.layangi.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.layangi.myapplication.R;

public class MenuActivity extends AppCompatActivity {

    Button viewB,btnview,btnEview;
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.w("menu","Start menu");


        id = getIntent().getStringExtra("id");
        Log.w("menu","id:"+id);

        viewB = (Button) findViewById(R.id.btnView);
        btnview = findViewById(R.id.btnview);
        btnEview = findViewById(R.id.btnEview);
        Button addBtn = findViewById(R.id.btnAdd);

        viewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, ViewActivity.class).putExtra("id",id));
            }
        });

        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, BankDataViewActivity.class).putExtra("id",id));
            }
        });

        btnEview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, EmailDataActivity.class).putExtra("id",id));
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MenuActivity.this, DataStoreActivity.class).putExtra("id",id));

            }
        });

    }
}
