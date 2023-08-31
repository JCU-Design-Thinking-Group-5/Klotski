package com.example.newklotski;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Klotski.db";
    public static final int DATABASE_VERSION = 1;
    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        //The constructor in the parent class must be called through super
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(id integer primary key autoincrement,game_name varchar(64),steps Integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "alter table user add sex varchar(8)";
        db.execSQL(sql);
    }
}