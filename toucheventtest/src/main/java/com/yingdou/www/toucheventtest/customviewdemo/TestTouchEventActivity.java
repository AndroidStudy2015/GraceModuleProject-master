package com.yingdou.www.toucheventtest.customviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import com.yingdou.www.toucheventtest.R;

public class TestTouchEventActivity extends AppCompatActivity {

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_touch_event);

//        scrollView = (ScrollView) findViewById(R.id.scrollView);
//
//        scrollView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.scrollTo(0, (int) (10*3));
//
//            }
//        },1000);
    }
}
