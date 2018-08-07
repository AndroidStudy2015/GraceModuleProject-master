package com.yingdou.www.toucheventtest.customviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyLinearLayoutOut extends LinearLayout {

    private int mLastY;

    public MyLinearLayoutOut(Context context) {
        super(context);
        init(context);
    }

    public MyLinearLayoutOut(Context context, AttributeSet attrs) {
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


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int y = (int) ev.getY();


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:
                int dY = y - mLastY;
                scrollBy(0,-dY);


                break;


            case MotionEvent.ACTION_UP:

                break;

            default:

                break;
        }
        mLastY = y;

        return true
                ;
    }
}
