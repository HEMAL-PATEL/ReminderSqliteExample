package com.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.remindersqliteexample.R;
import com.pojo.ItemCheckList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 4/25/2017.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CustomAdapter>{

    public Context context;
    public ArrayList<ItemCheckList> itemsArrayList;
    public CallBack callBack;

    public CheckListAdapter(Context context , ArrayList<ItemCheckList> itemsArrayList , CallBack callBack){
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.callBack = callBack;
    }

    @Override
    public CustomAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_for_fragment , null);
        CustomAdapter customAdapter = new CustomAdapter(view);
        return customAdapter;
    }

    @Override
    public void onBindViewHolder(final CustomAdapter holder, int position) {
        final ItemCheckList items = itemsArrayList.get(position);
        holder.event.setText(items.getTitle());
        holder.timedate.setText(items.getDetails());
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
        return itemsArrayList.size();
    }

    public interface CallBack{
        void show(int position, ItemCheckList items);
    }

    public class CustomAdapter extends RecyclerView.ViewHolder{
        @Bind(R.id.check_list_title)
        TextView event;
        @Bind(R.id.check_list_details)
        TextView timedate;

        View view;
        public CustomAdapter(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
