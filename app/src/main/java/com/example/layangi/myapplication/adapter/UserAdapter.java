package com.example.layangi.myapplication.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import com.example.layangi.myapplication.codes.EncryptDecrypt;
import com.example.layangi.myapplication.R;
import com.example.layangi.myapplication.codes.users;


public class UserAdapter extends ArrayAdapter {

    Context context;//which activity I am creating the cells into
    LayoutInflater layoutInflater;//create and repeat layout

    String key = "";
    static List<users> data;

    private static SparseBooleanArray selectedItemId;
    private @LayoutRes
    int resourceId;

    public UserAdapter(List<users> data, Context context,String key) {
        super(context, R.layout.info, data);
        this.resourceId = R.layout.info;
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

    public  Object getItems(int position) {
        //get item based on the position
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Log.w("Adapter","starttt 6");

        //create the cell (view) and populate it with an element of the array
        final EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

        if(view == null)
            view = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(resourceId,null);

        TextView tv1 = view.findViewById(R.id.text4);
        TextView tv2 = view.findViewById(R.id.text6);
        TextView tv3 = view.findViewById(R.id.text8);
        Log.w("adapter","data:"+data.get(position).getUid());
        try {
            tv1.setText(encryptDecrypt.decrypt(data.get(position).getuName(),key));
            tv2.setText(encryptDecrypt.decrypt(data.get(position).getuEmail(),key));
            tv3.setText(encryptDecrypt.decrypt(data.get(position).getuPassword(),key));
//            tv1.setText(data.get(position).getuName());
//            tv2.setText(data.get(position).getuEmail());
//            tv3.setText(data.get(position).getuPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.w("Adapter","starttt 7");
        return view;
    }

    public void removeData(users object){

        data.remove(object);
        //notifyDataSetChanged();
    }

    public List<users> getUserData(){
        return data;
    }

    public static void toggleSelection(int position){
        selectView(position,!selectedItemId.get(position));
    }

    public static void selectView(int position, boolean value) {

        if(value){

            selectedItemId.put(position,value);
        }else{

            selectedItemId.delete(position);
        }
        //notifyDataSetChanged();
    }
    public void removeSelection(){
        selectedItemId = new SparseBooleanArray();
        //notifyDataSetChanged();
    }

    public int getSelectedCount(){
        return selectedItemId.size();
    }

    public SparseBooleanArray getSelectedItemIds(){
        return selectedItemId;
    }
}
