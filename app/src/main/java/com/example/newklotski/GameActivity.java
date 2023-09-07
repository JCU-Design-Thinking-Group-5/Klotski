package com.example.newklotski;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private TextView steps_text;
    private int steps = 0;
    private Boolean isOver = false;

    // Constructor
    public GameActivity(){
        gameActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize UI elements
        steps_text = (TextView) findViewById(R.id.count_steps);
        TextView textView = findViewById(R.id.game_title);
        AssetManager mgr=getResources().getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/SIMLI.ttf");
        textView.setTypeface(tf);

        // Button for refreshing the game
        Button button_refresh = findViewById(R.id.refresh);
        button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView gameview = findViewById(R.id.game_view);
                gameview.refreshGame();
            }
        });

        // Button for returning to the main activity
        Button button_returnMain = findViewById(R.id.returnMain);
        button_returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // Create a database helper and get a writable database instance
        DataHelper dbHelper = new DataHelper(this, DataHelper.DATABASE_NAME,null,1);
        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();

        // Update the best steps display
        updateBestSteps();
    }

    // Update the best steps display
    private void updateBestSteps() {
        TextView best_text = findViewById(R.id.best_steps);
        DataHelper dbHelper = new DataHelper(this, DataHelper.DATABASE_NAME,null,1);
        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();

        // Query the minimum steps from the database
        Cursor cursor = sqliteDatabase.rawQuery("SELECT MIN(steps) FROM user WHERE game_name = 0", null);
        if(cursor.moveToFirst()){
            int minSteps = cursor.getInt(0);
            best_text.setText(String.valueOf(minSteps));
        } else {
            best_text.setText("No Record");
        }

        cursor.close();
        sqliteDatabase.close();
    }

    // Clear the steps count
    public void clearSteps(){
        steps = 0;
        showSteps();
    }

    // Display the steps count
    public void showSteps(){
        steps_text.setText(steps+"");
    }

    // Update the steps count
    public void addSteps(int s){
        steps = s;
        showSteps();
    }

    // Check if the game is over and show the dialog box
    public void checkOver(Boolean flag){
        isOver = flag;
        if (isOver){
            // Add vibration effect
            Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
            long[] patter = {1000, 1000, 2000, 50};
            vibrator.vibrate(1000);
            vibrator.cancel();

            // Use LayoutInflater to load custom dialog box
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogView = inflater.inflate(R.layout.dialog_normal_layout, null);

            // Find "give_money" TextView from the view and set its click listener
            TextView give_money = dialogView.findViewById(R.id.give_money);
            give_money.setClickable(true);
            give_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GameActivity.this, GiveMoneyActivity.class);
                    startActivity(intent);
                }
            });

            // Create dialog box
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(dialogView);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // Set click listeners for positive and negative buttons
            TextView positiveTextView = dialogView.findViewById(R.id.positiveTextView);
            positiveTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(GameActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            TextView negativeTextView = dialogView.findViewById(R.id.negativeTextView);
            negativeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    clearSteps();
                    GameView gameview = findViewById(R.id.game_view);
                    gameview.refreshGame();
                    updateBestSteps();
                }
            });

            // Show the dialog box
            DataStorage();
            updateBestSteps();
            dialog.show();
        }
    }

    private static  GameActivity gameActivity =  null;

    public static GameActivity getGameActivity() {
        return gameActivity;
    }

    // Store game completion data in the database
    private void DataStorage() {
        DataHelper dbHelper1 = new DataHelper(this, DataHelper.DATABASE_NAME, null, 2);
        SQLiteDatabase sqliteDatabase1 = dbHelper1.getWritableDatabase();

        // Query the min steps
        Cursor cursor = sqliteDatabase1.rawQuery("SELECT MIN(steps) FROM user WHERE game_name = 0", null);

        ContentValues values1 = new ContentValues();
        values1.put("game_name", GameView.level);
        values1.put("steps", steps);

        // Insert the new game completion record
        sqliteDatabase1.insert("user", null, values1);

        sqliteDatabase1.close();
    }
}
