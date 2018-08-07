package com.yingdou.www.toucheventtest.customviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollViewOut extends ScrollView {
    public MyScrollViewOut(Context context) {
        super(context);
        init(context);
    }

    public MyScrollViewOut(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {




    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action==MotionEvent.ACTION_DOWN){
            return false;
        }else {
            return true;

        }

    }
}
