package com.example.newklotski;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.example.newklotski.GameActivity;

public class GameView extends GridLayout {

    public int blockwidth = getCardWidth();
    public static int steps = 0;
    public static Boolean isOver = false;
    public static int level = 0;

    //棋子矩阵
    static com.example.newklotski.GamePuzzle[][] puzzleMap = new com.example.newklotski.GamePuzzle[4][5];

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public void initGameView(){

        //set background color
        setBackgroundColor(Color.parseColor("#ede0c8"));

        setColumnCount(4);
        setRowCount(5);

        //add puzzles
        if (level == 0){
            refreshMatrix();
            addPuzzles_0();
        } else if (level == 1){
            refreshMatrix();
            addPuzzles_1();
        } else if (level == 2){
            refreshMatrix();
            addPuzzles_2();
        } else if (level == 3){
            refreshMatrix();
            addPuzzles_3();
        } else if (level == 4){
            refreshMatrix();
            addPuzzles_4();
        } else {
            refreshMatrix();
            addPuzzles_5();
        }

        printPuzzles();
    }

    public void refreshGame(){
        for(int i = 0; i < 4; i ++){
            for(int j = 0; j < 5; j++){
                puzzleMap[i][j] = null;
            }
        }
        removeAllViews();
        GameActivity.getGameActivity().clearSteps();
        initGameView();
        steps = 0;
        isOver = false;
    }

    public void refreshMatrix() {
        for(int i = 0; i < 4; i ++){
            for(int j = 0; j < 5; j++){
                puzzleMap[i][j] = null;
            }
        }
    }

    //get card width according to screen
    public int getCardWidth(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        return Math.min(width / 4, height / 5);
    }

    private void addPuzzle(int width,int height,int x,int y,String name,int columnspan,int rowspan){
        com.example.newklotski.GamePuzzle puzzle = new com.example.newklotski.GamePuzzle(getContext(),name,columnspan,rowspan);
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.columnSpec = GridLayout.spec(x, columnspan);
        param.rowSpec = GridLayout.spec(y, rowspan);
        param.width = width * columnspan;
        param.height = height * rowspan;
        puzzle.setLayoutParams(param);
        for (int i = x; i < x + columnspan; i++){
            for (int j = y; j < y + rowspan; j++){
                puzzleMap[i][j] = puzzle;
            }
        }
        addView(puzzle);
    }

    private void addPuzzles_0(){
        addPuzzle(blockwidth,blockwidth,0,0,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,1,0,"  ",2,2);
        addPuzzle(blockwidth,blockwidth,3,0,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,0,2,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,1,2,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,3,2,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,0,4,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,1,3,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,2,3,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,3,4,"  ",1,1);
    }

    private void addPuzzles_1(){
        addPuzzle(blockwidth,blockwidth,0,1,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,1,0,"  ",2,2);
        addPuzzle(blockwidth,blockwidth,3,1,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,0,3,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,1,2,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,3,3,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,0,0,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,3,0,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,1,3,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,2,3,"  ",1,1);
    }

    private void addPuzzles_2(){
        addPuzzle(blockwidth,blockwidth,1,3,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,1,0,"  ",2,2);
        addPuzzle(blockwidth,blockwidth,2,3,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,0,2,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,1,2,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,3,2,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,0,0,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,0,1,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,3,0,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,3,1,"  ",1,1);
    }

    private void addPuzzles_3(){
        addPuzzle(blockwidth,blockwidth,0,0,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,2,0,"  ",2,2);
        addPuzzle(blockwidth,blockwidth,1,0,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,2,3,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,2,2,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,3,3,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,0,2,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,0,3,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,1,2,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,1,3,"  ",1,1);
    }

    private void addPuzzles_4(){
        addPuzzle(blockwidth,blockwidth,0,0,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,1,0,"  ",2,2);
        addPuzzle(blockwidth,blockwidth,3,0,"  ",1,2);
        addPuzzle(blockwidth,blockwidth,1,3,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,1,4,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,1,2,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,0,2,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,0,3,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,3,2,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,3,3,"  ",1,1);
    }

    private void addPuzzles_5(){
        addPuzzle(blockwidth,blockwidth,2,4,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,1,0,"  ",2,2);
        addPuzzle(blockwidth,blockwidth,0,4,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,0,3,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,1,2,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,2,3,"  ",2,1);
        addPuzzle(blockwidth,blockwidth,0,1,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,0,2,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,3,1,"  ",1,1);
        addPuzzle(blockwidth,blockwidth,3,2,"  ",1,1);
    }

    void printPuzzles(){
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 4; j++){
                if(puzzleMap[j][i]!=null)
                    System.out.print(puzzleMap[j][i].name+" ");
                else
                    System.out.print("null");
            }
            System.out.println();
        }
    }
}

