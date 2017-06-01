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

import com.database.DatabseManage;
import com.pojo.Items;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DatabaseAdapter.CallBack{

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    public DatabaseAdapter adapter;
    public ArrayList<Items> itemsArrayList;
    public DatabseManage databseManage;


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
        databseManage = new DatabseManage(getApplicationContext());
        itemsArrayList = databseManage.getCheckList();
        adapter = new DatabaseAdapter(getBaseContext() , itemsArrayList , this);
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
    public void show(int position, Items items) {
        Intent intent = new Intent(MainActivity.this , CheckListActivity.class);
        intent.putExtra("title" , items.getTitle());
        intent.putExtra("id" , items.getId());
        intent.putExtra("details" , items.getDetails());
        startActivity(intent);
    }
}
