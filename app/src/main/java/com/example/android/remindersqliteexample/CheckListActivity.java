package com.example.android.remindersqliteexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.CheckListAdapter;
import com.database.DatabseManage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pojo.Items;

import java.util.ArrayList;

public class CheckListActivity extends AppCompatActivity implements DatabaseAdapter.CallBack{

    String details;
    ArrayList<Items> itemsArrayList = new ArrayList<>();
    public CheckListAdapter adapter;
    RecyclerView recyclerView;
    public EditText edit;
    Button ok;
    String json;
    public DatabseManage databseManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final String title = intent.getExtras().getString("title");
        details = intent.getExtras().getString("details");
        final long id = intent.getExtras().getLong("id");
        edit = (EditText) findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ok = (Button) findViewById(R.id.ok_button);
        databseManage = new DatabseManage(getApplicationContext());

        Toast.makeText(this, ""+details, Toast.LENGTH_SHORT).show();


        itemsArrayList = getArrayList(details);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsArrayList.add(new Items(edit.getText().toString()));
                json = convertToString(itemsArrayList);
                Toast.makeText(CheckListActivity.this, ""+json, Toast.LENGTH_SHORT).show();
                databseManage.update(id , title , json);
                edit.setText("");
            }
        });

        adapter = new CheckListAdapter(getApplicationContext() , itemsArrayList , this);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<Items> getArrayList(String value){
        if (!value.isEmpty()){
            Gson gson = new Gson();
            ArrayList<Items> newArrayList = gson.fromJson(value , new TypeToken<ArrayList<Items>>(){}.getType());
            if (!newArrayList.isEmpty()&& newArrayList.size()>0){
                return newArrayList;
            }
            else {
                Toast.makeText(this, "new array list null", Toast.LENGTH_SHORT).show();
            }

        }
        return null;
    }

    public String convertToString(ArrayList<Items> itemsArrayList){
        ArrayList<Items> convertToString = new ArrayList<>();
        for (Items items:itemsArrayList){
            convertToString.add(items);
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(convertToString);
        return jsonString;
    }

    @Override
    public void show(int position, Items items) {

    }
}
