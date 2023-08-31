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

//class of pieces
public class GamePuzzle extends FrameLayout {

    private TextView label;
    public int blockwidth = getCardWidth();
    public String name;
    private int columnspan;
    private int rowspan;
    private int direction = 5;

    public GamePuzzle(Context context, String name, final int columnspan, final int rowspan) {
        super(context);

        //call parameter in
        this.name = name;
        this.columnspan = columnspan;
        this.rowspan = rowspan;

        //set font
        label = new TextView(getContext());
        label.setGravity(Gravity.CENTER);
        AssetManager mgr=getResources().getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/SIMLI.ttf");
        label.setTypeface(tf);
        label.setText(name);
        label.setTextSize(70);
        label.setBackgroundColor(Color.parseColor("#33ffffff"));
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10,10,10,10);
        addView(label,lp);

        //set pieces color
        if (rowspan+columnspan == 4){
            setBackgroundResource(R.drawable.jiang);

        }else if (rowspan + columnspan == 2) {
            setBackgroundResource(R.drawable.kuai2);
        }else
            setBackgroundResource(R.drawable.kuai);


        //catch user's action
        setOnTouchListener(new View.OnTouchListener() {

            private float startX,startY,endX,endY,startPX,startPY;
            private float puzzleX,puzzleY;
            Boolean canLeft,canRight,canUp,canDown = false;

            @Override
            public boolean onTouch(View v, MotionEvent event){

                //The top-left coordinate of the piece
                puzzleX = v.getX();
                puzzleY = v.getY();

                //User gesture action
                switch(event.getAction()){

                    //finger press
                    case MotionEvent.ACTION_DOWN:
                        //position of finger press
                        startX = event.getX();
                        startY = event.getY();
                        //Start view position
                        startPX = v.getX();
                        startPY = v.getY();
                        canLeft = canLeft(v);
                        canRight = canRight(v);
                        canUp = canUp(v);
                        canDown = canDown(v);
                        break;

                    //Finger slide
                    case MotionEvent.ACTION_MOVE:

                        //Horizontal and vertical travel distance (plus or minus)
                        endX = event.getX()-startX;
                        endY = event.getY()-startY;

                        //Absolute travel distance
                        float distance = Math.max((Math.abs(endX)),Math.abs(endY));

                        // When a piece can move in a 90 degree direction towards both
                        // Up left, up right, down left, down right
                        // Top left
                        if (direction == 5){
                            if (canUp && canLeft){
                                if (endX<-5 && (Math.abs(endX)-5>Math.abs(endY))){
                                    direction = 1;
                                    endY = 0;
                                } else if (endY<-5 && (Math.abs(endX)-5<Math.abs(endY))){
                                    direction = 3;
                                    endX = 0;
                                }
                                else
                                    break;
                            }else if (canUp && canRight){
                                if (endX>5 && (Math.abs(endX)-5>Math.abs(endY))){
                                    direction = 2;
                                } else if (endY<-5 && (Math.abs(endX)-5<Math.abs(endY)))
                                    direction = 3;
                                else
                                    break;
                            }else if (canDown && canLeft){
                                if (Math.abs(endX)-5>Math.abs(endY)){
                                    direction = 1;
                                } else if (endY>5 && (Math.abs(endX)-5<Math.abs(endY)))
                                    direction = 4;
                                else
                                    break;
                            }else if (canDown && canRight){
                                if (Math.abs(endX)-5>Math.abs(endY)){
                                    direction = 2;
                                } else if (endY>5 && (Math.abs(endX)-5<Math.abs(endY)))
                                    direction = 4;
                                else
                                    break;
                            }else{
                                //Judge the direction
                                if (Math.abs(endX)>Math.abs(endY)){
                                    if(endX<-5 && canLeft){
                                        //left
                                        swipeLeft(v,event,distance);
                                    }else if(endX>5 && canRight){
                                        //right
                                        swipeRight(v,event,distance);
                                    }
                                }else {
                                    if (endY<-5 && canUp){
                                        //up
                                        swipeUp(v,event,distance);
                                    }else if(endY>5 && canDown){
                                        //down
                                        swipeDown(v,event,distance);
                                    }
                                }
                            }
                            switch (direction){
                                case 1:
                                    swipeLeft(v,event,distance);
                                    break;
                                case 2:
                                    swipeRight(v,event,distance);
                                    break;
                                case 3:
                                    swipeUp(v,event,distance);
                                    break;
                                case 4:
                                    swipeDown(v,event,distance);
                                    break;
                            }
                        }
                        break;

                    //lift finger
                    case MotionEvent.ACTION_UP:
                        endX = v.getX()-startPX;
                        endY = v.getY()-startPY;
                        if (Math.abs(endX)>Math.abs(endY)){
                            if (endX < 0){
                                if(Math.abs(endX)>5){
                                    setToLeft(v);
                                }else {
                                    v.setX(startPX);
                                    direction = 5;
                                }
                            }else if (endX > 0){
                                if(Math.abs(endX)>5){
                                    setToRight(v);
                                }else {
                                    v.setX(startPX);
                                    direction = 5;
                                }
                            }
                        }else {
                            if (endY < -0){
                                if(Math.abs(endY)>5){
                                    setToUp(v);
                                }else {
                                    v.setY(startPY);
                                    direction = 5;
                                }
                            }else if (endY > 0){
                                if(Math.abs(endY)>5){
                                    setToDown(v);
                                }else {
                                    v.setY(startPY);
                                    direction = 5;
                                }
                            }
                        }
                        break;
                }
                return true;
            }


            private void setToLeft(View v){
                int indexX = (int) (startPX / blockwidth);
                int indexY = (int) (startPY / blockwidth);
                for (int i = indexY; i < indexY + rowspan; i++){
                    if(com.example.newklotski.GameView.puzzleMap[indexX][i] != null){
                        com.example.newklotski.GameView.puzzleMap[indexX-1][i] = com.example.newklotski.GameView.puzzleMap[indexX][i];
                        com.example.newklotski.GameView.puzzleMap[indexX+columnspan-1][i] = null;
                    }
                }
                v.setX(startPX - blockwidth);
                com.example.newklotski.GameView.steps ++;
                GameActivity.getGameActivity().addSteps(com.example.newklotski.GameView.steps);
                direction = 5;
                GameActivity.getGameActivity().checkOver(isOver(v));
            }
            private void setToRight(View v){
                int indexX = (int) (startPX / blockwidth);
                int indexY = (int) (startPY / blockwidth);
                for (int i = indexY; i < indexY + rowspan; i++){
                    if(com.example.newklotski.GameView.puzzleMap[indexX][i] != null){
                        com.example.newklotski.GameView.puzzleMap[indexX+columnspan][i] = com.example.newklotski.GameView.puzzleMap[indexX][i];
                        com.example.newklotski.GameView.puzzleMap[indexX][i] = null;
                    }
                }
                v.setX(startPX + blockwidth);
                com.example.newklotski.GameView.steps ++;
                GameActivity.getGameActivity().addSteps(com.example.newklotski.GameView.steps);
                direction = 5;
                GameActivity.getGameActivity().checkOver(isOver(v));
            }
            private void setToUp(View v){
                int indexX = (int) (startPX / blockwidth);
                int indexY = (int) (startPY / blockwidth);
                for (int i = indexX; i < indexX + columnspan; i++){
                    if(com.example.newklotski.GameView.puzzleMap[i][indexY] != null){
                        com.example.newklotski.GameView.puzzleMap[i][indexY - 1] = com.example.newklotski.GameView.puzzleMap[i][indexY];
                        com.example.newklotski.GameView.puzzleMap[i][indexY+rowspan-1] = null;
                    }
                }
                v.setY(startPY - blockwidth);
                com.example.newklotski.GameView.steps ++;
                GameActivity.getGameActivity().addSteps(com.example.newklotski.GameView.steps);
                direction = 5;
                GameActivity.getGameActivity().checkOver(isOver(v));
            }
            private void setToDown(View v){
                int indexX = (int) (startPX / blockwidth);
                int indexY = (int) (startPY / blockwidth);
                for (int i = indexX; i < indexX + columnspan; i++){
                    if(com.example.newklotski.GameView.puzzleMap[i][indexY] != null) {
                        com.example.newklotski.GameView.puzzleMap[i][indexY + rowspan] = com.example.newklotski.GameView.puzzleMap[i][indexY];
                        com.example.newklotski.GameView.puzzleMap[i][indexY] = null;
                    }
                }
                v.setY(startPY + blockwidth);
                com.example.newklotski.GameView.steps ++;
                GameActivity.getGameActivity().addSteps(com.example.newklotski.GameView.steps);
                direction = 5;
                GameActivity.getGameActivity().checkOver(isOver(v));
            }
        });
    }

    //User gestures move pieces
    private void swipeLeft(View v, MotionEvent event, float distance){
        v.setX(v.getX() - distance);
    }

    private void swipeRight(View v, MotionEvent event, float distance){
        v.setX(v.getX() + distance);
    }

    private void swipeUp(View v, MotionEvent event, float distance){
        v.setY(v.getY() - distance);
    }

    private void swipeDown(View v, MotionEvent event, float distance){
        v.setY(v.getY() + distance);
    }

    //get card width according to screen
    public int getCardWidth(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        return Math.min(width / 4, height / 5);
    }

    //if move to left
    private Boolean canLeft(View v){
        Boolean flag = true;
        int indexX = (int) (v.getX() / blockwidth);
        int indexY = (int) (v.getY() / blockwidth);
        if (indexX == 0){
            flag = false;
        }
        else{
            for (int i = indexY; i < indexY + rowspan; i++) {
                if (com.example.newklotski.GameView.puzzleMap[indexX - 1][i] != null)
                    flag = false;
            }
        }
        return flag;
    }

    //if move to right
    private Boolean canRight(View v){
        Boolean flag = true;
        int indexX = (int) (v.getX() / blockwidth);
        int indexY = (int) (v.getY() / blockwidth);
        if (indexX + columnspan >= 4){
            flag = false;
        }
        else{
            for (int i = indexY; i < indexY + rowspan; i++) {
                if (com.example.newklotski.GameView.puzzleMap[indexX + columnspan][i] != null)
                    flag = false;
            }
        }
        return flag;
    }

    //if move up
    private Boolean canUp(View v){
        Boolean flag = true;
        int indexX = (int) (v.getX() / blockwidth);
        int indexY = (int) (v.getY() / blockwidth);
        if (indexY == 0){
            flag = false;
        }
        else{
            for (int i = indexX; i < indexX + columnspan; i++) {
                if (com.example.newklotski.GameView.puzzleMap[i][indexY - 1] != null)
                    flag = false;
            }
        }
        return flag;
    }

    //if move down
    private Boolean canDown(View v){
        Boolean flag = true;
        int indexX = (int) (v.getX() / blockwidth);
        int indexY = (int) (v.getY() / blockwidth);
        if (indexY +  rowspan >= 5){
            flag = false;
        }
        else{
            for (int i = indexX; i < indexX + columnspan; i++) {
                if (com.example.newklotski.GameView.puzzleMap[i][indexY + rowspan] != null)
                    flag = false;
            }
        }
        return flag;
    }

    private Boolean isOver(View v){
        if((rowspan + columnspan == 4)&&((int) (v.getX() / blockwidth) == 1)&&((int) (v.getY() / blockwidth) == 3)){
            return true;
        }
        else
            return false;
    }

}
