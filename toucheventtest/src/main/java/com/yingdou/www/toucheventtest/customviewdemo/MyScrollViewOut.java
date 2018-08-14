package com.yingdou.www.toucheventtest.customviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
//应该是废弃了,处理scrollView嵌套滑动，应该是最内部的scrollview和其直接父View（线性布局）之间处理 见MyLinearLayoutOut和ScrollView
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
