package com.grace.www.gracemoduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.grace.www.qrcode.app.CaptureActivity;
import com.grace.www.videoplayer.MyVideoPlayerActivity;

public class MainActivity extends AppCompatActivity {

    private Button mBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBt = findViewById(R.id.bt);
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_QRCODE);

            }
        });


        findViewById(R.id.bt_open_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyVideoPlayerActivity.class);
                startActivity(intent);
            }
        });


    }


    private static final int REQUEST_QRCODE = 0x01;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                // TODO: 2018/7/6 处理扫码结果
                if (resultCode == Activity.RESULT_OK) {
                    String code = data.getStringExtra("SCAN_RESULT");
                    Toast.makeText(MainActivity.this, code, Toast.LENGTH_LONG).show();
                }
                break;
        }

        // TODO: 2018/7/6 处理扫相册图片结果

        if (resultCode == CaptureActivity.ALBUM_RESULT_CODE) {
            String code = data.getStringExtra(CaptureActivity.RESULT_ALBUM_DATA);
            Toast.makeText(MainActivity.this, code, Toast.LENGTH_LONG).show();

        }
    }


}