package com.example.yue.game2048;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    private Card[][] cardsMap = new Card[4][4];//记录卡片组成的矩阵数组
    private List<Point> emptyPoint = new ArrayList<Point>();


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
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardwidth = (Math.min(w,h)-10)/4;//通过选取手机长和宽最小值，减去合适布局值除以4得到每个卡片的宽度
        addCards(cardwidth,cardwidth);
        startGame();
    }

    private void addCards(int cardWidth,int cardHeight){
        Card c ;
        for(int y = 0; y < 4 ; y++){
            for (int x = 0 ;x < 4; x++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);

                cardsMap[x][y] = c;
            }
        }

    }

    private void startGame(){
        for (int y = 0;y < 4; y ++){
            for (int x = 0; x < 4 ;x ++) {
                cardsMap[x][y].setNum(0);//清除控件的数字
            }
        }
        addRandonNum();
        addRandonNum();

    }

    private void addRandonNum(){
        //生成随机数
        emptyPoint.clear();//清空

        for (int y = 0;y < 4; y ++){
            for (int x = 0; x < 4 ;x++){
                if(cardsMap[x][y].getNum() <= 0){
                    emptyPoint.add(new Point(x,y));
                }
            }
        }

        Point p = emptyPoint.remove((int)(Math.random()*emptyPoint.size()));//获取一个控件移除控件字符
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
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
