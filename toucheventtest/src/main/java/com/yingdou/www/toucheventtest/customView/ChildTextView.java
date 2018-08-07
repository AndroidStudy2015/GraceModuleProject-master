package com.yingdou.www.toucheventtest.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class ChildTextView extends AppCompatTextView {
    public ChildTextView(Context context) {
        super(context);
    }

    public ChildTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("touch", "ChildTextView--->dispatchTouchEvent--->子--->分发事件开始***********************");


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                Log.e("touch", "子  ACTION_DOWN");

                break;

            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(false);
                Log.e("touch", "子  ACTION_move");


                break;


            case MotionEvent.ACTION_UP:

                break;

            default:

                break;
        }


        boolean b = super.dispatchTouchEvent(ev);
        Log.e("touch", "子--->分发事件结束+" + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch", "ChildTextView--->onTouchEvent--->子--->处理事件开始***********************");
        boolean b = super.onTouchEvent(event);
        b = true;
        Log.e("touch", "子--->处理事件结束+" + b + event.getAction());
        return b;
    }


}


