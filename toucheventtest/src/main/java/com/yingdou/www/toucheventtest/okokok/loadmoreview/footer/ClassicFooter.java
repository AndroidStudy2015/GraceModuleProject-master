package com.yingdou.www.toucheventtest.okokok.loadmoreview.footer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.yingdou.www.toucheventtest.R;

public class ClassicFooter extends BaseFooter {

    private TextView tv;
    private ImageView iv;
    private ObjectAnimator animator;

    public ClassicFooter(Context context) {
        this(context, null);


    }

    public ClassicFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.footer_classic, this, true);
        tv = view.findViewById(R.id.tv);
        iv = view.findViewById(R.id.iv);


    }

    @Override
    public void onLoadNoMore() {
        tv.setText("已经到底了~");
        iv.setVisibility(INVISIBLE);
        if (animator != null) {
            animator.end();
        }

    }


    @Override
    public void onLoadingMore() {
        tv.setText("正在加载...");
        float rotation = iv.getRotation();
        animator = ObjectAnimator.ofFloat(iv, "rotation", rotation, rotation + 180f * 10);

        animator.setDuration(3000);//时间1s
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.start();
        iv.setVisibility(VISIBLE);

    }

    @Override
    public void onLoadMoreFinish() {
        tv.setText("加载完成");
        iv.setVisibility(INVISIBLE);
        if (animator != null) {

            animator.end();
        }
    }
}
