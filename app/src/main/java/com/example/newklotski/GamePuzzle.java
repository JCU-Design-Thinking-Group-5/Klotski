package com.example.newklotski;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.Gravity;

import com.example.newklotski.GameActivity;

// Class representing a game puzzle, extending FrameLayout
public class GamePuzzle extends FrameLayout {

    private TextView label;
    public int blockwidth = getCardWidth(); // Width of the puzzle block
    public String name; // Name of the puzzle
    private int columnspan; // Number of columns occupied by the puzzle
    private int rowspan; // Number of rows occupied by the puzzle
    private int direction = 5; // Direction of movement (5 represents no movement)

    // Constructor for the GamePuzzle class
    public GamePuzzle(Context context, String name, final int columnspan, final int rowspan) {
        super(context);

        // Initialize puzzle properties
        this.name = name;
        this.columnspan = columnspan;
        this.rowspan = rowspan;

        // Create and customize the label for the puzzle
        label = new TextView(getContext());
        label.setGravity(Gravity.CENTER);
        AssetManager mgr = getResources().getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/SIMLI.ttf");
        label.setTypeface(tf);
        label.setText(name);
        label.setTextSize(70);
        label.setBackgroundColor(Color.parseColor("#33ffffff"));
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 10, 10);
        addView(label, lp);

        // Set the background color of the puzzle based on its size
        if (rowspan + columnspan == 4) {
            setBackgroundResource(R.drawable.jiang);
        } else if (rowspan + columnspan == 2) {
            setBackgroundResource(R.drawable.kuai2);
        } else {
            setBackgroundResource(R.drawable.kuai);
        }

        // Set a touch listener to capture user actions on the puzzle
        setOnTouchListener(new View.OnTouchListener() {

            private float startX, startY, endX, endY, startPX, startPY;
            private float puzzleX, puzzleY;
            Boolean canLeft, canRight, canUp, canDown = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Get the top-left coordinates of the puzzle
                puzzleX = v.getX();
                puzzleY = v.getY();

                // Process user gesture action
                switch (event.getAction()) {

                    // Finger press
                    case MotionEvent.ACTION_DOWN:
                        // Record the position of the finger press
                        startX = event.getX();
                        startY = event.getY();
                        // Record the initial position of the puzzle view
                        startPX = v.getX();
                        startPY = v.getY();
                        canLeft = canLeft(v); // Check if puzzle can move left
                        canRight = canRight(v); // Check if puzzle can move right
                        canUp = canUp(v); // Check if puzzle can move up
                        canDown = canDown(v); // Check if puzzle can move down
                        break;

                    // Finger slide
                    case MotionEvent.ACTION_MOVE:

                        // Calculate the horizontal and vertical travel distances (delta)
                        endX = event.getX() - startX;
                        endY = event.getY() - startY;

                        // Calculate the absolute travel distance
                        float distance = Math.max((Math.abs(endX)), Math.abs(endY));

                        // When a puzzle can be moved in a 90-degree direction
                        // (up-left, up-right, down-left, down-right)
                        if (direction == 5) {
                            if (canUp && canLeft) {
                                if (endX < -5 && (Math.abs(endX) - 5 > Math.abs(endY))) {
                                    direction = 1; // Set direction to up-left
                                    endY = 0; // Cancel the vertical movement
                                } else if (endY < -5 && (Math.abs(endX) - 5 < Math.abs(endY))) {
                                    direction = 3; // Set direction to up-right
                                    endX = 0; // Cancel the horizontal movement
                                } else {
                                    break;
                                }
                            } else if (canUp && canRight) {
                                if (endX > 5 && (Math.abs(endX) - 5 > Math.abs(endY))) {
                                    direction = 2; // Set direction to up-right
                                } else if (endY < -5 && (Math.abs(endX) - 5 < Math.abs(endY))) {
                                    direction = 3; // Set direction to up-left
                                } else {
                                    break;
                                }
                            } else if (canDown && canLeft) {
                                if (Math.abs(endX) - 5 > Math.abs(endY)) {
                                    direction = 1; // Set direction to down-left
                                } else if (endY > 5 && (Math.abs(endX) - 5 < Math.abs(endY))) {
                                    direction = 4; // Set direction to down-right
                                                               } else {
                                    break;
                                }
                            } else if (canDown && canRight) {
                                if (Math.abs(endX) - 5 > Math.abs(endY)) {
                                    direction = 2; // Set direction to down-right
                                } else if (endY > 5 && (Math.abs(endX) - 5 < Math.abs(endY))) {
                                    direction = 4; // Set direction to down-left
                                } else {
                                    break;
                                }
                            } else {
                                // Determine the direction based on the travel distances
                                if (Math.abs(endX) > Math.abs(endY)) {
                                    if (endX < -5 && canLeft) {
                                        // Move left
                                        swipeLeft(v, event, distance);
                                    } else if (endX > 5 && canRight) {
                                        // Move right
                                        swipeRight(v, event, distance);
                                    }
                                } else {
                                    if (endY < -5 && canUp) {
                                        // Move up
                                        swipeUp(v, event, distance);
                                    } else if (endY > 5 && canDown) {
                                        // Move down
                                        swipeDown(v, event, distance);
                                    }
                                }
                            }
                            switch (direction) {
                                case 1:
                                    swipeLeft(v, event, distance);
                                    break;
                                case 2:
                                    swipeRight(v, event, distance);
                                    break;
                                case 3:
                                    swipeUp(v, event, distance);
                                    break;
                                case 4:
                                    swipeDown(v, event, distance);
                                    break;
                            }
                        }
                        break;

                    // Finger lift
                    case MotionEvent.ACTION_UP:
                        endX = v.getX() - startPX;
                        endY = v.getY() - startPY;
                        if (Math.abs(endX) > Math.abs(endY)) {
                            if (endX < 0) {
                                if (Math.abs(endX) > 5) {
                                    setToLeft(v);
                                } else {
                                    v.setX(startPX);
                                    direction = 5;
                                }
                            } else if (endX > 0) {
                                if (Math.abs(endX) > 5) {
                                    setToRight(v);
                                } else {
                                    v.setX(startPX);
                                    direction = 5;
                                }
                            }
                        } else {
                            if (endY < -0) {
                                if (Math.abs(endY) > 5) {
                                    setToUp(v);
                                } else {
                                    v.setY(startPY);
                                    direction = 5;
                                }
                            } else if (endY > 0) {
                                if (Math.abs(endY) > 5) {
                                    setToDown(v);
                                } else {
                                    v.setY(startPY);
                                    direction = 5;
                                }
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
}


private void setToLeft(View v){
    // Calculate the indices of the block
    int indexX = (int) (startPX / blockwidth);
    int indexY = (int) (startPY / blockwidth);
    
    // Move the block to the left
    for (int i = indexY; i < indexY + rowspan; i++){
        if(com.example.newklotski.GameView.puzzleMap[indexX][i] != null){
            com.example.newklotski.GameView.puzzleMap[indexX-1][i] = com.example.newklotski.GameView.puzzleMap[indexX][i];
            com.example.newklotski.GameView.puzzleMap[indexX+columnspan-1][i] = null;
        }
    }
    
    // Update the position of the View object
    v.setX(startPX - blockwidth);
    
    // Increment the step count
    com.example.newklotski.GameView.steps++;
    
    // Update the step count displayed in the game activity
    GameActivity.getGameActivity().addSteps(com.example.newklotski.GameView.steps);
    
    // Set the direction to 5 (representing left movement)
    direction = 5;
    
    // Check if the game is over
    GameActivity.getGameActivity().checkOver(isOver(v));
}

private void setToRight(View v){
    // Calculate the indices of the block
    int indexX = (int) (startPX / blockwidth);
    int indexY = (int) (startPY / blockwidth);
    
    // Move the block to the right
    for (int i = indexY; i < indexY + rowspan; i++){
        if(com.example.newklotski.GameView.puzzleMap[indexX][i] != null){
            com.example.newklotski.GameView.puzzleMap[indexX+columnspan][i] = com.example.newklotski.GameView.puzzleMap[indexX][i];
            com.example.newklotski.GameView.puzzleMap[indexX][i] = null;
        }
    }
    
    // Update the position of the View object
    v.setX(startPX + blockwidth);
    
    // Increment the step count
    com.example.newklotski.GameView.steps++;
    
    // Update the step count displayed in the game activity
    GameActivity.getGameActivity().addSteps(com.example.newklotski.GameView.steps);
    
    // Set the direction to 5 (representing right movement)
    direction = 5;
    
    // Check if the game is over
    GameActivity.getGameActivity().checkOver(isOver(v));
}

private void setToUp(View v){
    // Calculate the indices of the block
    int indexX = (int) (startPX / blockwidth);
    int indexY = (int) (startPY / blockwidth);
    
    // Move the block upwards
    for (int i = indexX; i < indexX + columnspan; i++){
        if(com.example.newklotski.GameView.puzzleMap[i][indexY] != null){
            com.example.newklotski.GameView.puzzleMap[i][indexY - 1] = com.example.newklotski.GameView.puzzleMap[i][indexY];
            com.example.newklotski.GameView.puzzleMap[i][indexY + rowspan - 1] = null;
        }
    }
    
    // Update the position of the View object
    v.setY(startPY - blockwidth);
    
    // Increment the step count
    com.example.newklotski.GameView.steps++;
    
    // Update the step count displayed in the game activity
    GameActivity.getGameActivity().addSteps(com.example.newklotski.GameView.steps);
    
    // Set the direction to 5 (representing up movement)
    direction = 5;
    
    // Check if the game is over
    GameActivity.getGameActivity().checkOver(isOver(v));
}

private void setToDown(View v){
    // Calculate the indices of the block
    int indexX = (int) (startPX / blockwidth);
    int indexY = (int) (startPY / blockwidth);
    
    // Move the block downwards
    for (int i = indexX; i < indexX + columnspan; i++){
        if(com.example.newklotski.GameView.puzzleMap[i][indexY] != null) {
            com.example.newklotski.GameView.puzzleMap[i][indexY + rowspan] = com.example.newklotski.GameView.puzzleMap[i][indexY];
            com.example.newklotski.GameView.puzzleMap[i][indexY] = null;
        }
    }
    
    // Update the position of the View object
    v.setY(startPY + blockwidth);
    
