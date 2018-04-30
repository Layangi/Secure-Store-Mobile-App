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
import com.example.layangi.myapplication.adapter.BankAdapter;
import com.example.layangi.myapplication.codes.BankData;
import com.example.layangi.myapplication.helpers.DBHelpers;

import java.util.List;

public class BankDataViewActivity extends AppCompatActivity {

    FrameLayout frag1;
    BankAdapter bankAdapter1;
    ListView listView1;
    List<BankData> Bdata;
    DBHelpers dbHelpers;
    int deleteItem;
    String key = "";
    String id ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_data_view);

        id = getIntent().getStringExtra("id");
        dbHelpers = new DBHelpers(this);
        Bdata = dbHelpers.readAllBankData(Integer.parseInt(id));
        key = dbHelpers.getAlias(Integer.parseInt(id));

        listView1 = findViewById(R.id.bankList);
        bankAdapter1 = new BankAdapter(Bdata, this,key);
        listView1.setAdapter(bankAdapter1);



        frag1 = findViewById(R.id.frag1);

        new CountDownTimer(1000,
                100) {
            @Override
            public void onTick(long millisUntilFinished) {
                frag1.setAnimation(AnimationUtils.loadAnimation(BankDataViewActivity.this, android.R.anim.slide_in_left));
            }

            @Override
            public void onFinish() {

            }


        }.start();


        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeBankItemFromList(position);

                return true;
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               // updateItemList(Bdata.get(position).getStoreId());
            }
        });

    }


    private void removeBankItemFromList(final int position) {

        AlertDialog.Builder alert = new AlertDialog.Builder(
                BankDataViewActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes

                deleteItem = Bdata.get(position).getStoreId();
                dbHelpers.deleteBankItem(deleteItem,Integer.parseInt(id));
                startActivity(new Intent(BankDataViewActivity.this, BankDataViewActivity.class).putExtra("id",id));


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

    /*public void updateItemList(int id){

        Intent intent = new Intent(this,UpdateBankDataActivity.class);
        intent.putExtra("data",id);
        BankDataViewActivity.this.startActivity(intent);
    }*/

}
