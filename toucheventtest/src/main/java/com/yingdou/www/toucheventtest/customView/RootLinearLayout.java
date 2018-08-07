package com.yingdou.www.toucheventtest.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class RootLinearLayout extends LinearLayout {
    public RootLinearLayout(Context context) {
        super(context);
    }

    public RootLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("touch", "RootLinearLayout--->dispatchTouchEvent--->根--->分发事件开始***********************");
        boolean b = super.dispatchTouchEvent(ev);

        Log.e("touch", "根--->分发事件结束+" + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch", "RootLinearLayout--->onTouchEvent--->根--->处理事件开始***********************");
        boolean b = super.onTouchEvent(event);

        Log.e("touch", "根--->处理事件结束+" + b + event.getAction());
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("touch", "RootLinearLayout--->onInterceptTouchEvent--->根--->拦截事件开始***********************");
        boolean b = super.onInterceptTouchEvent(ev);
        Log.e("touch", "根--->拦截事件结束+" + b);

        return b;
    }
}
