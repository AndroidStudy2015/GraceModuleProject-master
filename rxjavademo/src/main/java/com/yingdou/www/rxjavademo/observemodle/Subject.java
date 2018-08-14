package com.yingdou.www.rxjavademo.observemodle;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    /**
     * 1. 观察者列表 observerList
     */
    List<Observer> mObserverList = new ArrayList<>();

    /**
     * 2. 添加观察者 addObserver
     *
     * @param observer
     */
    public void addObserver(Observer observer) {
        mObserverList.add(observer);
    }

    /**
     * 3. 移除观察者 removeObserver
     *
     * @param observer
     */

    public void removeObserver(Observer observer) {
        mObserverList.remove(observer);
    }

    /**
     * 4. 通知所有观察者notifyAllObserver
     */


    public void notifyAllObserver(String state) {
        for (Observer observer : mObserverList) {
            observer.update(state);
        }

    }

    /**
     * 5. 状态改变的方法
     */
    public abstract void changeState(String state);


}
