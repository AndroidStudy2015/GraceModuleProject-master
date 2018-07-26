package com.yingdou.www.anydialog.dialog;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.yingdou.www.anydialog.R;

public class MyProgressDialog extends Dialog {

    Context mContext;
    private ObjectAnimator rotation;

    public MyProgressDialog(@NonNull Context context) {
        super(context, R.style.ProgressDialog);
        mContext = context;
    }

    public MyProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);



        makeFullActivity();
        makeRotation();
        otherSetting();

    }

    private void otherSetting() {
//        点击外部可取消
        setCanceledOnTouchOutside(false);
//        对话框是不是可以取消，如果设为false，点击回退按钮，也不会取消对话框，只能主动调用disimiss()取消
        setCancelable(true);
    }

    private void makeFullActivity() {
        WindowManager.LayoutParams lay = getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        Rect rect = new Rect();
        View view = getWindow().getDecorView();//decorView是window中的最顶层view，可以从window中获取到decorView
        view.getWindowVisibleDisplayFrame(rect);
        //不设置这个里的宽高的话，dialog永远是包裹内容
        lay.height = dm.heightPixels - rect.top;
        lay.width = dm.widthPixels;
    }

    private void makeRotation() {
        ImageView ivProgress = findViewById(R.id.iv_progress);


        rotation = ObjectAnimator.ofFloat(ivProgress, "rotation", 0, 360);
        rotation.setDuration(1570);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setRepeatCount(Animation.INFINITE);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        rotation.end();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        rotation.start();
    }
}
