package com.example.layangi.myapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.layangi.myapplication.R;
import com.example.layangi.myapplication.adapter.EmailAdapter;
import com.example.layangi.myapplication.codes.EmailData;
import com.example.layangi.myapplication.helpers.DBHelpers;

import java.util.List;

public class EmailDataActivity extends AppCompatActivity {

    EmailAdapter emailAdapter;
    ListView listView2;
    List<EmailData> Edata;
    DBHelpers dbHelpers;
    int deleteItem;
    FrameLayout frag2;
    String key = "";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_data);

        id = getIntent().getStringExtra("id");
        dbHelpers = new DBHelpers(this);
        Edata = dbHelpers.readAllEmailData(Integer.parseInt(id));

        listView2 = findViewById(R.id.emailList);
        key = dbHelpers.getAlias(Integer.parseInt(id));
        emailAdapter = new EmailAdapter(Edata, this,key);
        listView2.setAdapter(emailAdapter);

        frag2 = findViewById(R.id.frag2);

        new CountDownTimer(1000,
                100) {
            @Override
            public void onTick(long millisUntilFinished) {
                frag2.setAnimation(AnimationUtils.loadAnimation(EmailDataActivity.this, android.R.anim.slide_in_left));
            }

            @Override
            public void onFinish() {

            }


        }.start();

        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeEmailItemFromList(position);

                return true;
            }
        });
    }

    private void removeEmailItemFromList(int position) {

        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                EmailDataActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                deleteItem = Edata.get(deletePosition).getStoreId();
                dbHelpers.deleteEmailItem(deleteItem,Integer.parseInt(id));
                startActivity(new Intent(EmailDataActivity.this, EmailDataActivity.class).putExtra("id",id));

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();
    }

}
