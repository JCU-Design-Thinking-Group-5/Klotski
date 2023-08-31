package com.example.newklotski;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> rankList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        listView = findViewById(R.id.rank_list);

        // Set up the ArrayAdapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rankList);
        listView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        DataHelper dbHelper = new DataHelper(this, DataHelper.DATABASE_NAME, null, 1);
        SQLiteDatabase sqliteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqliteDatabase.rawQuery("SELECT game_name, steps FROM user ORDER BY game_name ASC, steps ASC", null);

        while (cursor.moveToNext()) {
            String gameName = cursor.getString(cursor.getColumnIndex("game_name"));
            int steps = cursor.getInt(cursor.getColumnIndex("steps"));

            // Format the game level
            String formattedGameName = "Level " + (Integer.parseInt(gameName) + 1);

            rankList.add(formattedGameName + ": " + steps + " steps");
        }
        cursor.close();
        sqliteDatabase.close();

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();
    }

}
