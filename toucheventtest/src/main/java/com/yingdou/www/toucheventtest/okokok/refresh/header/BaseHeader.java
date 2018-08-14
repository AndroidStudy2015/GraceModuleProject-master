package com.yingdou.www.toucheventtest.okokok.refresh.header;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yingdou.www.toucheventtest.okokok.refresh.RefreshLayout;

public abstract class BaseHeader extends LinearLayout implements RefreshLayout.IHeaderControl {


    public BaseHeader(Context context) {
        this(context, null);


    }

    public BaseHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public abstract void onPullDownRefresh(int curPullDownHeight, int headerHeight, float percent);

    @Override
    public abstract void onRealseRefresh(int curPullDownHeight, int headerHeight, float percent);

    @Override
    public abstract void onRefreshing(int curPullDownHeight, int headerHeight, float percent);
    @Override
    public abstract void onRefreshFinish() ;
}
