package com.grace.www.videoplayer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

public class MyDialoge extends Dialog {

    private FrameLayout rootView;

    MyVideoPlayerActivity context;

    public MyDialoge(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = (MyVideoPlayerActivity) context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_full_screen_viedo_player);
        rootView = findViewById(R.id.fl_root);

    }


    public FrameLayout getDialogRootView() {
        return rootView;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        context.turnFullScreen();

    }

    //    在这里变为小屏幕 效果最佳
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        context.turnSmallScreen();

    }
}
