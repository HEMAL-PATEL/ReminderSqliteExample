package com.example.android.remindersqliteexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pojo.Items;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 4/25/2017.
 */

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.CustomAdapter>{

    public Context context;
    public ArrayList<Items> itemsArrayList;
    public CallBack callBack;

    public DatabaseAdapter(Context context , ArrayList<Items> itemsArrayList , CallBack callBack){
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.callBack = callBack;

    }

    @Override
    public CustomAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleview , parent, false);
        CustomAdapter customAdapter = new CustomAdapter(view);
        return customAdapter;
    }

    @Override
    public void onBindViewHolder(final CustomAdapter holder, int position) {
        final Items items = itemsArrayList.get(position+1);
        holder.event.setText(items.getTitle());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.show(holder.getAdapterPosition() , items);
            }
        });
        /*
        if (isChecked()==true){
            Toast.makeText(context, "True", Toast.LENGTH_SHORT).show();
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
        }

        */
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size()-1;
    }


    public interface CallBack{
        void show(int position, Items items);
    }

    public class CustomAdapter extends RecyclerView.ViewHolder{


        @Bind(R.id.event)
        TextView event;
        @Bind(R.id.time_and_date)
        TextView timedate;

        View view;
        public CustomAdapter(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }


}
