package com.yingdou.www.toucheventtest.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ParentLinearLayout extends LinearLayout {
    public ParentLinearLayout(Context context) {
        super(context);
    }

    public ParentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("touch", "ParentLinearLayout--->dispatchTouchEvent--->父--->分发事件开始***********************");
        boolean b = super.dispatchTouchEvent(ev);
        Log.e("touch", "父--->分发事件结束+" + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch", "ParentLinearLayout--->onTouchEvent--->父--->处理事件开始***********************");
        boolean b = super.onTouchEvent(event);
b=true;
        Log.e("touch", "父--->处理事件结束+" + b + event.getAction());
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("touch", "ParentLinearLayout--->onInterceptTouchEvent--->父--->拦截事件开始***********************");
//        boolean b = super.onInterceptTouchEvent(ev);
boolean b;
        int action = ev.getAction();
        if (action==MotionEvent.ACTION_DOWN){
            b=false;

        }else {
            b=true;

        }

        Log.e("touch", "父--->拦截事件结束+" + b);


        return b;
//        return true;
    }
}
