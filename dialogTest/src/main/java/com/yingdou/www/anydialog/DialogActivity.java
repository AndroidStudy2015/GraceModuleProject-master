package com.yingdou.www.anydialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yingdou.www.anydialog.dialog.FullActivityDialog;
import com.yingdou.www.anydialog.dialog.MyProgressDialog;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtFullActivityDialog;
    private Button mBtProgressDialog;

    private FullActivityDialog mFullActivityDialog;
    private MyProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        initView();

        setListener();

        initDialog();
    }

    private void initDialog() {
        mFullActivityDialog = new FullActivityDialog(this);
        mFullActivityDialog.setCanceledOnTouchOutside(true);
        mFullActivityDialog.setCancelable(true);


        mProgressDialog = new MyProgressDialog(this);

    }

    private void setListener() {
        mBtFullActivityDialog.setOnClickListener(this);
        mBtProgressDialog.setOnClickListener(this);

    }

    private void initView() {
        mBtFullActivityDialog = findViewById(R.id.bt_full_activity_dialog);
        mBtProgressDialog = findViewById(R.id.bt_progress_dialog);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_full_activity_dialog) {

            mFullActivityDialog.show();
        }

        if (i==R.id.bt_progress_dialog){
            mProgressDialog.show();
        }
    }
}
