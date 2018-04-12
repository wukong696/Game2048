package com.example.yue.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class GameView extends GridLayout {

    public GameView(Context context) {
        super(context);

        initGameView();
    }


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initGameView();
    }

    private void initGameView(){
        setOnTouchListener(new View.OnTouchListener() {

            private float startX,startY,offsetX,offsetY;//定义手指滑动开始位置和离开位置
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN://手指按下
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP://手指离开
                        offsetX = event.getX()-startX;//计算偏移量
                        offsetY = event.getY()-startY;

                        if(Math.abs(offsetX)>Math.abs(offsetY)){
                            //判断例如左下方向的滑动，用绝对值判断，如果x绝对值大于y的绝对值就是水平方向的
                            if(offsetX < -5){
                                swipeLeft();
                                Toast.makeText(MyApplition.getContext(),"left",Toast.LENGTH_SHORT).show();
                            }else if(offsetX > 5 ){
                                swipeRight();
                                Toast.makeText(MyApplition.getContext(),"right",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            if (offsetY < -5) {
                                swipeUp();
                                Toast.makeText(MyApplition.getContext(), "up", Toast.LENGTH_SHORT).show();
                            } else if (offsetY > 5) {
                                swipeDown();
                                Toast.makeText(MyApplition.getContext(), "down", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void swipeLeft(){

    }
    private void swipeRight(){

    }
    private void swipeUp(){

    }
    private void swipeDown(){

    }
}
