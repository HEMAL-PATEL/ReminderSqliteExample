package com.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.remindersqliteexample.R;
import com.pojo.Items;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 6/2/2017.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CustomAdapter>{


    public Context context;
    public ArrayList<Items> itemsArrayList;
    public CallBack callBack;

    public CheckListAdapter(Context context , ArrayList<Items> itemsArrayList , CallBack callBack){
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.callBack = callBack;

    }

    @Override
    public CustomAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_list , null);
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

        if (items.checkBoxId==1){
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked() == true){
                    items.setCheckBoxId(1);
                    callBack.checkBoxClicked(items.getId() , items);

                }else {
                    items.setCheckBoxId(0);
                    callBack.disClickCheckBox(items.getId() , items);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size()-1;
    }

    public interface CallBack{
        void show(int position, Items items);
        void checkBoxClicked(long id , Items items);
        void disClickCheckBox(long id , Items items);
    }

    public class CustomAdapter extends RecyclerView.ViewHolder{


        @Bind(R.id.item_title)
        TextView event;
        @Bind(R.id.item_details)
        TextView timedate;
        @Bind(R.id.id_check)
        CheckBox checkBox;

        View view;
        public CustomAdapter(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
