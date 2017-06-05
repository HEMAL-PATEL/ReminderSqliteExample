package com.example.android.remindersqliteexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.database.CheckListAdapter;
import com.database.DatabaseManager;
import com.pojo.ItemCheckList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CheckListAdapter.CallBack{

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    public CheckListAdapter adapter;
    public ArrayList<ItemCheckList> itemsArrayList;
    public DatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        callDatabaseData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , PutTodo.class);
                startActivity(intent);
            }
        });
    }

    public void callDatabaseData(){
        databaseManager = new DatabaseManager(getApplicationContext());
        itemsArrayList = databaseManager.getCheckList();
        adapter = new CheckListAdapter(getBaseContext() , itemsArrayList , this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        callDatabaseData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void show(int position, ItemCheckList items) {
        Intent intent = new Intent(MainActivity.this , CheckItemActivity.class);
        intent.putExtra("title" , items.getTitle());
        intent.putExtra("id" , items.getId());
        intent.putExtra("details" , items.getDetails());
        startActivity(intent);
    }
}
