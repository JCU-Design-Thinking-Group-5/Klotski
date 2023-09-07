package com.example.newklotski;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChooseGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
        
        // Get references to the TextViews
        TextView text_0 = findViewById(R.id.game_0);
        TextView text_1 = findViewById(R.id.game_1);
        TextView text_2 = findViewById(R.id.game_2);
        TextView text_3 = findViewById(R.id.game_3);
        TextView text_4 = findViewById(R.id.game_4);
        TextView text_5 = findViewById(R.id.game_5);
        
        // Set custom font to the TextViews
        AssetManager mgr = getResources().getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/BlackOpsOne-Regular.ttf");
        text_0.setTypeface(tf);
        text_1.setTypeface(tf);
        text_2.setTypeface(tf);
        text_3.setTypeface(tf);
        text_4.setTypeface(tf);
        text_5.setTypeface(tf);
    }

    // Function to set the selected game to level 0 and start the GameActivity
    public void setGame_0(View view) {
        com.example.newklotski.GameView.level = 0;
        Intent intent = new Intent(ChooseGame.this, GameActivity.class);
        startActivity(intent);
        saveSelectedLevel(1); // Save the selected level as 1
    }

    // Function to set the selected game to level 1 and start the GameActivity
    public void setGame_1(View view) {
        com.example.newklotski.GameView.level = 1;
        Intent intent = new Intent(ChooseGame.this, GameActivity.class);
        startActivity(intent);
        saveSelectedLevel(2); // Save the selected level as 2
    }

    // Function to set the selected game to level 2 and start the GameActivity
    public void setGame_2(View view) {
        com.example.newklotski.GameView.level = 2;
        Intent intent = new Intent(ChooseGame.this, GameActivity.class);
        startActivity(intent);
        saveSelectedLevel(3); // Save the selected level as 3
    }

    // Function to set the selected game to level 3 and start the GameActivity
    public void setGame_3(View view) {
        com.example.newklotski.GameView.level = 3;
        Intent intent = new Intent(ChooseGame.this, GameActivity.class);
        startActivity(intent);
        saveSelectedLevel(4); // Save the selected level as 4
    }

    // Function to set the selected game to level 4 and start the GameActivity
    public void setGame_4(View view) {
        com.example.newklotski.GameView.level = 4;
        Intent intent = new Intent(ChooseGame.this, GameActivity.class);
        startActivity(intent);
        saveSelectedLevel(5); // Save the selected level as 5
    }

    // Function to set the selected game to level 5 and start the GameActivity
    public void setGame_5(View view) {
        com.example.newklotski.GameView.level = 5;
        Intent intent = new Intent(ChooseGame.this, GameActivity.class);
        startActivity(intent);
        saveSelectedLevel(6); // Save the selected level as 6
    }

    // Function to save the selected level in SharedPreferences
    private void saveSelectedLevel(int level) {
        SharedPreferences sharedPreferences = getSharedPreferences("KlotskiPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("selectedLevel", level);
        editor.apply();
    }
}
