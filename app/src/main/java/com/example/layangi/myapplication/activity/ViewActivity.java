package com.example.layangi.myapplication.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.layangi.myapplication.R;
import com.example.layangi.myapplication.adapter.UserAdapter;
import com.example.layangi.myapplication.codes.users;
import com.example.layangi.myapplication.helpers.DBHelpers;

import java.util.List;

public class ViewActivity extends AppCompatActivity {
    FrameLayout frag1;
    UserAdapter userAdapter1;
    ListView listViewU;
    List<users> uInfo;
    DBHelpers dbHelpers;
    String key = "";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Log.w("View", "Created");

        id = getIntent().getStringExtra("id");

        dbHelpers = new DBHelpers(this);
        uInfo = dbHelpers.readAllUserInfo(Integer.parseInt(id));
        key = dbHelpers.getAlias(Integer.parseInt(id));

        listViewU = findViewById(R.id.userList);
        userAdapter1 = new UserAdapter(uInfo, this, key);
        listViewU.setAdapter(userAdapter1);

        frag1 = findViewById(R.id.frag1);

        new CountDownTimer(1000,
                100) {
            @Override
            public void onTick(long millisUntilFinished) {
                frag1.setAnimation(AnimationUtils.loadAnimation(ViewActivity.this, android.R.anim.slide_in_left));
            }

            @Override
            public void onFinish() {

            }

        }.start();

    }

}
