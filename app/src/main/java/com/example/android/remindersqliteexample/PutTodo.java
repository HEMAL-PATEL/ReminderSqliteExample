package com.example.android.remindersqliteexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.DatabaseManager;
import com.google.gson.Gson;
import com.pojo.ItemCheckList;

import java.util.ArrayList;

public class PutTodo extends AppCompatActivity {

    EditText titleEditText;
    EditText detailsEditText;
    public DatabaseManager databaseManager;
    String title;
    String details;

    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleEditText = (EditText) findViewById(R.id.edit_1);
        detailsEditText = (EditText) findViewById(R.id.edit_2);

        title = titleEditText.getText().toString();
        details = detailsEditText.getText().toString();

        databaseManager = new DatabaseManager(getApplicationContext());

        ok = (Button) findViewById(R.id.button2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                ArrayList<ItemCheckList> itemsArrayList = new ArrayList<ItemCheckList>();
                itemsArrayList.add(new ItemCheckList(detailsEditText.getText().toString()));
                String json = gson.toJson(itemsArrayList);
                databaseManager.createCheckList(titleEditText.getText().toString() , json);
                Toast.makeText(PutTodo.this, ""+json, Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

}
