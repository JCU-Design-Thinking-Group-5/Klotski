package com.example.newklotski;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button rule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView title_textView = findViewById(R.id.Big_title);
        TextView rule_text = findViewById(R.id.rule);
        TextView start_text = findViewById(R.id.start);
        TextView choose_text = findViewById(R.id.choose_game);
        TextView exit_text = findViewById(R.id.exit);
        TextView rank_text = findViewById(R.id.rank);
        AssetManager mgr=getResources().getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/QwitcherGrypen-Bold.ttf");
        Typeface tf2 = Typeface.createFromAsset(mgr, "fonts/BlackOpsOne-Regular.ttf");
        title_textView.setTypeface(tf);
        start_text.setTypeface(tf2);
        rule_text.setTypeface(tf2);
        choose_text.setTypeface(tf2);
        exit_text.setTypeface(tf2);
        rank_text.setTypeface(tf2);

        rule = findViewById(R.id.rule);
        rule.setOnClickListener(v -> showGameRule());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_choose) {
            Intent intent = new Intent(MainActivity.this,ChooseGame.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            finishAffinity();
        } else if (id == R.id.nav_communicate) {
            Intent intent = new Intent(MainActivity.this,ContactUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_money) {
            Intent intent = new Intent(MainActivity.this,GiveMoneyActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_server) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/sGGCb7D8"));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    static final int REQUEST_LEVEL = 1;
    public void playGame(View view){
        Intent intent = new Intent(MainActivity.this,GameActivity.class);
        startActivity(intent);
    }

    public void rankPage(View view){
        Intent intent = new Intent(MainActivity.this,RankActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("KlotskiPreferences", MODE_PRIVATE);
        int selectedLevel = sharedPreferences.getInt("selectedLevel", -1);  // set '-1' to express choosing nothing

        TextView middleTextView = findViewById(R.id.middle_text);
        if (selectedLevel != -1) {
            middleTextView.setText(String.format("Current Level: %d", selectedLevel));
        }
    }


    public void chooseGame(View view){
        Intent intent = new Intent(MainActivity.this,ChooseGame.class);
        startActivity(intent);
    }

    //method to show game rules
    public void showGameRule(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Rules")
                .setMessage("Click the level button to select different levels, move the 2x2 square to the bottom middle position to win, and the rank button can view the number of steps completed by different levels. Have a nice game!")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
    public void exitGame(View view){
        finishAffinity();
    }

}
