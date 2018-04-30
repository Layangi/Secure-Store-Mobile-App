package com.example.layangi.myapplication.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.layangi.myapplication.R;
import com.example.layangi.myapplication.codes.EmailData;
import com.example.layangi.myapplication.codes.EncryptDecrypt;

import java.util.List;

/**
 * Created by Lakshika on 30/03/2018.
 */

public class EmailAdapter extends ArrayAdapter {

    Context context;//which activity I am creating the cells into
    LayoutInflater layoutInflater;//create and repeat layout

    String key = "";
    static List<EmailData> data;

    private @LayoutRes
    int resourceId;

    public EmailAdapter(List<EmailData> data, Context context,String key) {

        super(context, R.layout.row, data);
        this.resourceId = R.layout.row;
        this.data = data;
        this.context = context;
        this.key = key;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public  Object getItem(int position) {
        //get item based on the position
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //create the cell (view) and populate it with an element of the array
        final EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

        if(view == null)
            view = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(resourceId,null);

        TextView tv1 = view.findViewById(R.id.text1);
        TextView tv2 = view.findViewById(R.id.text2);
        TextView tv3 = view.findViewById(R.id.text3);
        try {
            tv1.setText(encryptDecrypt.decrypt(data.get(position).getEmail(),key));
            tv2.setText(encryptDecrypt.decrypt(data.get(position).getEmailPwd(),key));
            tv3.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

}
