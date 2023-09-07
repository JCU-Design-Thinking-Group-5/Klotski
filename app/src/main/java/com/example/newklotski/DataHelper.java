package com.example.newklotski;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Klotski.db";
    public static final int DATABASE_VERSION = 1;

    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        // The constructor in the parent class must be called through super
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Called when the database is created for the first time
        // Create a table named "user" with columns: id (auto-incrementing integer), game_name (text), steps (integer)
        String sql = "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, game_name VARCHAR(64), steps INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Called when the database needs to be upgraded to a new version
        // Add a new column named "sex" (text) to the "user" table
        String sql = "ALTER TABLE user ADD COLUMN sex VARCHAR(8)";
        db.execSQL(sql);
    }
}
