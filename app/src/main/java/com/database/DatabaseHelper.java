package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by android on 5/31/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_list.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_TODO_LIST = "todo_home";
    public static final String TABLE_CHECK_LIST = "check_list";

    public static final String TABLE_CREATE_TODO_1 = "create table "  + TABLE_TODO_LIST + "( id "
            + " integer primary key autoincrement, title  text not null, details  text not null , time  text not null , date text not null );";

    public static final String CREATE_TABLE_CHECK_LIST = "create table "  + TABLE_CHECK_LIST + "( id "
            + " integer primary key autoincrement, title  text not null, details  text not null );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_TODO_1);
        db.execSQL(CREATE_TABLE_CHECK_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE_TODO_1);
        onCreate(db);
    }
}
