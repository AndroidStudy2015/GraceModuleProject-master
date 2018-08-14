package com.yingdou.www.rxjavademo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yingdou.www.rxjavademo.observemodle.MyObserver;
import com.yingdou.www.rxjavademo.observemodle.MySubject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {
    int i = 0;
    private Button bt;
    private long count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        bt = (Button) findViewById(R.id.bt);


        final List<String> dataList=new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            dataList.add("数据"+j);
        }


        Disposable flatmap = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                emitter.onNext(dataList);
            }
        }).concatMap(new Function<List<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings).delay(10000, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("flatmap", s + "=========");
            }
        });


//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Disposable subscribe = Observable.just("1","2","3")
//                        .debounce(5000, TimeUnit.MILLISECONDS)
//                        .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        i++;
//                        Log.e("debounce", s + "debounce:"+i);
//                    }
//                });
//            }
//        });

        bt.setBackgroundColor(Color.parseColor("#ffff00"));
        bt.setText("发送验证码");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int count = 5;

                Observable.interval(3, 1, TimeUnit.SECONDS)
                        .take(count+1)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                return (count - aLong-1) + "秒";
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                bt.setEnabled(false);
                                bt.setBackgroundColor(Color.parseColor("#ff0000"));
                            }
                        })
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String aLong) {
                                bt.setText(aLong);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                bt.setEnabled(true);
                                bt.setBackgroundColor(Color.parseColor("#ffff00"));
                                bt.setText("发送验证码");

                            }
                        });

            }
        });


        testObserve();


        Rxjavatest();
    }

    private void Rxjavatest() {
        Disposable subscribe = Observable.just("hello", "rxjava", "study").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("rxjava", s);
            }
        });


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
//                emitter.onComplete();//不能与onError同时实用，会直接报错
                emitter.onError(new Throwable("cuo"));
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("rxjava===", d.toString());
            }

            @Override
            public void onNext(String s) {
                Log.e("rxjava===", s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("rxjava===", "onError" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("rxjava===", "onComplete");

            }
        };
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void testObserve() {
        MySubject mySubject = new MySubject();

        MyObserver hanMeimei = new MyObserver("HanMeimei");
        MyObserver hanMeimei111 = new MyObserver("HanMeimei111");

        mySubject.addObserver(hanMeimei);
        mySubject.addObserver(hanMeimei111);


        mySubject.changeState("大米饭🍚");
    }
}
