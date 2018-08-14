package com.yingdou.www.rxjavademo.observemodle;

import android.util.Log;

public class MyObserver extends Observer {
    public MyObserver(String name) {
        this.name = name;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void update(String state) {
        Log.e("ccc", "我的名字叫：" + name + "被观察者" + "状态更新了：" + state);

    }
}
