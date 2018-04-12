package com.example.yue.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

                            }else if(offsetX > 5 ){
                                swipeRight();

                            }
                        }
                        else {
                            if (offsetY < -5) {
                                swipeUp();

                            } else if (offsetY > 5) {
                                swipeDown();
                                
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

        MainActivity.getMainActivity().clearScore();

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

        boolean marge = false;

        for (int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){

                for(int x1 = x+1;x1 <4 ; x1++){
                    if(cardsMap[x1][y].getNum()>0){

                        if(cardsMap[x][y].getNum()<=0){//如果左边的空的，把右边的放左边，右边清空
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;//最左面的是空的，右边有两个相同数字的控件，避免不会相加而只是位移，
                            marge = true;

                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){//如果一样的话，左边直接乘2，右边清空
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            marge = true;

                        }
                        break;
                    }
                }
            }
        }

        if (marge){
            addRandonNum();
            checkComplete();
        }

    }
    private void swipeRight(){

        boolean marge = false;

        for (int y = 0; y < 4; y++){
            for(int x = 3; x >= 0; x--){

                for(int x1 = x-1;x1 >=0 ; x1--){
                    if(cardsMap[x1][y].getNum()>0){

                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            marge = true;

                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            marge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (marge){
            addRandonNum();
            checkComplete();
        }


    }
    private void swipeUp(){

        boolean marge = false;

        for (int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){

                for(int y1 = y+1;y1 <4 ; y1++){
                    if(cardsMap[x][y1].getNum()>0){

                        if(cardsMap[x][y].getNum()<=0){//如果左边的空的，把右边的放左边，右边清空
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;//最左面的是空的，右边有两个相同数字的控件，避免不会相加而只是位移，
                            marge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){//如果一样的话，左边直接乘2，右边清空
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            marge = true;
                        }
                        break;

                    }
                }
            }
        }

        if (marge){
            addRandonNum();
            checkComplete();
        }


    }
    private void swipeDown(){

        boolean marge = false;

        for (int x = 0; x < 4; x++){
            for(int y = 3; y >= 0; y--){

                for(int y1 = y-1;y1 >= 0 ; y1--){
                    if(cardsMap[x][y1].getNum()>0){

                        if(cardsMap[x][y].getNum()<=0){//如果左边的空的，把右边的放左边，右边清空
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;//最左面的是空的，右边有两个相同数字的控件，避免不会相加而只是位移，
                            marge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){//如果一样的话，左边直接乘2，右边清空
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            marge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (marge){
            addRandonNum();
            checkComplete();
        }

    }

    public void checkComplete(){
        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum()==0||
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }

        if (complete) {
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }




}
