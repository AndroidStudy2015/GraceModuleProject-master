package com.yingdou.www.toucheventtest.okokok.refresh.header;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yingdou.www.toucheventtest.R;
import com.yingdou.www.toucheventtest.okokok.refresh.RefreshLayout;

public class ClassicHeader extends LinearLayout implements RefreshLayout.IHeaderControl {

    private TextView tv;
    private ImageView iv;
    private ObjectAnimator animator;

    public ClassicHeader(Context context) {
        this(context, null);


    }

    public ClassicHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.header_classic, this, false);
        addView(view);
        tv = view.findViewById(R.id.tv);
        iv = view.findViewById(R.id.iv);
    }

    @Override
    public void onPullDownRefresh(int curPullDownHeight, int headerHeight, float percent) {
        tv.setText("下拉刷新");
        iv.setRotation(curPullDownHeight * 3);

    }

    @Override
    public void onRealseRefresh(int curPullDownHeight, int headerHeight, float percent) {
        tv.setText("释放刷新");
        iv.setRotation(curPullDownHeight * 3);

    }

    @Override
    public void onRefreshing(int curPullDownHeight, int headerHeight, float percent) {
        tv.setText("正在刷新");

        float rotation = iv.getRotation();
        animator = ObjectAnimator.ofFloat(iv, "rotation", rotation, rotation + 180f * 10);

        animator.setDuration(3000);//时间1s
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.start();

    }

    @Override
    public void onRefreshFinish() {
        animator.end();
    }
}
