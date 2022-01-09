package com.example.a201501978__wiki;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<FindList> sample;

    //생성자
    public MyAdapter(Context context, ArrayList<FindList> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public FindList getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_item, null);

        TextView name = (TextView)view.findViewById(R.id.name);
        TextView category = (TextView)view.findViewById(R.id.category);
        TextView information = (TextView)view.findViewById(R.id.information);

        name.setText(sample.get(position).getName());
        category.setText(sample.get(position).getCategory());
        information.setText(sample.get(position).getInformation());

        //Button addBtn = (Button)view.findViewById(R.id.fixBtn);
        /*addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),paymentActivity.class);
                intent.putExtra("name",sample.get(position).getName());
                intent.putExtra("countText",countText.getText().toString());
                intent.putExtra("price",sample.get(position).getPrice());
                Toast.makeText(v.getContext(),"추가되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
         */
        return view;
    }
}
