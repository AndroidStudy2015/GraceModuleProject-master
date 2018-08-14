package com.yingdou.www.rxjavademo.observemodle;

public class MySubject extends Subject{

    @Override
    public void changeState(String state) {
//        do sth

        notifyAllObserver(state);
    }
}
