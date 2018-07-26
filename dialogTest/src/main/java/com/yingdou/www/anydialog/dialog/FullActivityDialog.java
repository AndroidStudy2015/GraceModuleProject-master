package com.yingdou.www.anydialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yingdou.www.anydialog.R;

public class FullActivityDialog extends Dialog {

    private Context mContext;

    //    这个全屏，没包含状态栏，是全部Activity的Dialog
    public FullActivityDialog(@NonNull Context context) {
        super(context, R.style.FullActivityDialog);
        mContext = context;
    }

    public FullActivityDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_full_activity);



        final Window dialogWindow = getWindow();
        final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        dialog的布局是match_parent，这里是WRAP_CONTENT，那么结果是WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        如果想要一个全屏幕高度的布局，不能使用match_parent，而是需要使用下面的
//        lp.height = ScreenSizeUtils.getInstance(mContext).getScreenHeight();

        lp.gravity = Gravity.BOTTOM;//用这个来指定dialog先是在什么位置
        dialogWindow.setAttributes(lp);



    }
}








