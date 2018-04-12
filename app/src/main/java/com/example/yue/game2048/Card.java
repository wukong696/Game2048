package com.example.yue.game2048;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
    private int num = 0;
    private TextView label;

    public Card(@NonNull Context context) {
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1,-1);//填充满整个父级容器
        lp.setMargins(10,10,0,0);
        addView(label,lp);//添加到主容器

        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if(num <= 0){
            label.setText("");
        }else {
            label.setText(num+"");

        }

    }


    public boolean equals(Card o) {
        return getNum()==o.getNum();//判断两张卡片数字是否一样
    }


}
