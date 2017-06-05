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

import com.database.CheckListItemAdapter;
import com.database.DatabaseManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pojo.ItemCheckList;

import java.util.ArrayList;

public class CheckItemActivity extends AppCompatActivity implements CheckListItemAdapter.CallBack{

    public static String details;
    public static String title;
    ArrayList<ItemCheckList> itemsArrayList = new ArrayList<>();
    public CheckListItemAdapter adapter;
    RecyclerView recyclerView;
    public EditText edit;
    Button ok;
    public static String json;
    public static long databseID;
    public DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        title = intent.getExtras().getString("title");
        details = intent.getExtras().getString("details");
        databseID = intent.getExtras().getLong("id");
        edit = (EditText) findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ok = (Button) findViewById(R.id.ok_button);
        databaseManager = new DatabaseManager(getApplicationContext());

        itemsArrayList = getArrayList(details);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsArrayList.add(new ItemCheckList(edit.getText().toString()));
                json = convertToString(itemsArrayList);
                //Toast.makeText(CheckItemActivity.this, ""+json, Toast.LENGTH_SHORT).show();
                databaseManager.updateCheckList(databseID, title , json);
                edit.setText("");
            }
        });

        adapter = new CheckListItemAdapter(getApplicationContext() , itemsArrayList , this);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<ItemCheckList> getArrayList(String value){
        if (!value.isEmpty()){
            Gson gson = new Gson();
            ArrayList<ItemCheckList> newArrayList = gson.fromJson(value , new TypeToken<ArrayList<ItemCheckList>>(){}.getType());
            if (!newArrayList.isEmpty()&& newArrayList.size()>0){
                return newArrayList;
            }
            else {
                Toast.makeText(this, "new array list null", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public String convertToString(ArrayList<ItemCheckList> itemsArrayList){
        ArrayList<ItemCheckList> convertToString = new ArrayList<>();
        for (ItemCheckList items:itemsArrayList){
            convertToString.add(items);
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(convertToString);
        return jsonString;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void show(int position, ItemCheckList items) {

    }

    @Override
    public void checkBoxClicked(long id, ItemCheckList items) {
        json = convertToString(itemsArrayList);
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        databaseManager.updateCheckList(databseID, title , json);
    }

    @Override
    public void disClickCheckBox(long id, ItemCheckList items) {
        json = convertToString(itemsArrayList);
        databaseManager.updateCheckList(databseID, title , json);
    }
}
