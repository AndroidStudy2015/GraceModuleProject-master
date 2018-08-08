package com.yingdou.www.toucheventtest.okokok.loadmoreview.footer;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class BaseFooter extends LinearLayout {



    public BaseFooter(Context context) {
        this(context, null);
    }

    public BaseFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public abstract void onLoadNoMore();

    public abstract void onLoadingMore();

    public abstract void onLoadMoreFinish();
}


