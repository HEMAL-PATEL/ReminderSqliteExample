package com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.pojo.Items;

import java.util.ArrayList;

/**
 * Created by android on 5/31/2017.
 */

public class DatabseManage {

    public SQLiteDatabase sqLiteDatabase;
    public DatabaseHelper databaseHelper;
    public Context context;
    public Items items;

    public DatabseManage(Context context){

        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        items = new Items();
    }


    public void open() throws SQLiteException{
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException{
        sqLiteDatabase.close();
    }

    public Items saveItems(final Items items){
        open();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("title" , items.title);
        contentValues.put("details" , items.details);
        contentValues.put("time" , items.time);
        contentValues.put("date" , items.date);

        final long id = sqLiteDatabase.insert(DatabaseHelper.TABLE_TODO_LIST , null , contentValues);
        items.id = id;
        return items;
    }


    public ArrayList<Items> getAllTodo(){
        open();
        final ArrayList<Items> arrayList = new ArrayList<>();
        final Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseHelper.TABLE_TODO_LIST , null);
        cursor.moveToLast();
        while (!cursor.isBeforeFirst()) {
            final Items number = new Items();

            // Fetch the desired value from the Cursor by column index
            number.id = cursor.getLong(0);
            number.title = cursor.getString(2);
            number.details = cursor.getString(3);
            number.time = cursor.getString(1);
            number.date = cursor.getString(4);

            // Add the object filled with appropriate data into the list
            arrayList.add(number);
            // Move the Cursor pointer to next for the next record to fetch
            cursor.moveToPrevious();
        }
        return arrayList;
    }


    public void createDatabase(String title , String details , String time , String date){
        items.date = date;
        items.title = title;
        items.time = time;
        items.details = details;
        saveItems(items);
    }
}
