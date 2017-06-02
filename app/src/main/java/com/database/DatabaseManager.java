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

public class DatabaseManager {

    public SQLiteDatabase sqLiteDatabase;
    public DatabaseHelper databaseHelper;
    public Context context;
    public Items items;

    public DatabaseManager(Context context){

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
        close();
        return items;
    }



    public Items makeCheckList(final Items items){
        open();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("title" , items.title);
        contentValues.put("details" , items.details);
        final long id = sqLiteDatabase.insert(DatabaseHelper.TABLE_CHECK_LIST , null , contentValues);
        items.id = id;
        close();
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
        close();
        return arrayList;
    }

    public ArrayList<Items> getCheckList(){
        open();
        final ArrayList<Items> arrayList = new ArrayList<>();
        final Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseHelper.TABLE_CHECK_LIST , null);
        cursor.moveToLast();
        while (!cursor.isBeforeFirst()) {
            final Items number = new Items();

            // Fetch the desired value from the Cursor by column index
            number.id = cursor.getLong(0);
            number.title = cursor.getString(1);
            number.details = cursor.getString(2);
            // Add the object filled with appropriate data into the list
            arrayList.add(number);
            // Move the Cursor pointer to next for the next record to fetch
            cursor.moveToPrevious();
        }
        close();
        return arrayList;
    }


    public void update(long id , String title , String details){
        open();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("id" , id);
        contentValues.put("title" , title);
        contentValues.put("details" , details);
        sqLiteDatabase.update(DatabaseHelper.TABLE_CHECK_LIST  ,contentValues , "id" + "=" + id , null);
        //sqLiteDatabase.update(DatabaseHelper.TABLE_CHECK_LIST  ,contentValues , "title" + "=" + "title" , null);
        close();
    }

    public void createCheckList(String title , String details){
        items.title = title;
        items.details = details;
        makeCheckList(items);
    }

    public void createDatabase(String title , String details , String time , String date){
        items.date = date;
        items.title = title;
        items.time = time;
        items.details = details;
        saveItems(items);
    }
}
