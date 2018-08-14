package com.yingdou.www.toucheventtest.customviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;
//与MyLinearLayoutOut一起使用解决滑动冲突
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
        init(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {


    }


    int mLastY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);

                break;

            case MotionEvent.ACTION_MOVE:
                int dY = y - mLastY;
                Log.e("ccc", dY + "============");
                if (dY > 0&& !canScrollVertically(-1)||dY < 0&& !canScrollVertically(1)) {
                    getParent().requestDisallowInterceptTouchEvent(false);

                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);

                }


                break;


            case MotionEvent.ACTION_UP:

                break;

            default:

                break;
        }
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }



}
