package com.fast.www.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NotificationDemo extends AppCompatActivity {

    private Button mBtCommonNotification;
    private Button mBtCancleCommonNotification;
    private Button mBtProgressNotification;
    private Button mBtFloatNotification;
    private NotificationManager mManager;
    private NotificationManager mProgressManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_notification_demo);
        mBtCommonNotification = findViewById(R.id.bt_common_notification);
        mBtCancleCommonNotification = findViewById(R.id.bt_cancle_common_notification);
        mBtProgressNotification = findViewById(R.id.bt_progress_notification);
        mBtFloatNotification = findViewById(R.id.bt_float_notification);

        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        setListener();
    }

    private void setListener() {
        mBtCommonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCommonNotification();

            }
        });
        mBtCancleCommonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.cancel(1);

            }
        });

        mBtProgressNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressNotification();

            }
        });


        mBtFloatNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFloatNotification();

            }
        });
    }

    private void initFloatNotification() {

    }

    /**
     * 普通通知，点击可以跳转
     */
    private void initCommonNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //设置PendingIntent，为了点击跳转
        Intent intent = new Intent(this, NotificationDemo.class);
        PendingIntent padingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // 构建通知
        final Notification notification = builder
                .setContentTitle("这是普通通知标题")
                .setContentText("这是普通通知内容。。。。。。。")
                .setColor(Color.parseColor("#EAA935"))
                //改变小图标颜色，因为小图标必须是alpha图标(郭林博客)，只有灰白色，通过这个颜色可以变色
                .setWhen(System.currentTimeMillis())//显示通知时间为现在
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)//通知小图标，必须是alpha，可以用android内部图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_email))//大图标
                .setContentIntent(padingIntent)//设置点击过后跳转的activity
//                .setDefaults(Notification.DEFAULT_SOUND)//设置声音
//                .setDefaults(Notification.DEFAULT_LIGHTS)//设置指示灯
//                .setDefaults(Notification.DEFAULT_VIBRATE)//设置震动
                .setDefaults(Notification.DEFAULT_ALL)//设置全部
                .build();//得到notification
        notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉

        mManager.notify(1, notification);//发通知，第一个参数为 id
    }

    /**
     * 包含进度条的通知，下载完成，点击可以跳转
     */
    private void initProgressNotification() {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        注意这里不要直接构造出notification，因为进度条通知栏，
// 本质就是不断地通过build构建新的进度条，不断地发送通知，要是在这里构建成功了，通知就不能变内容了
        mBuilder.setContentTitle("下载文件")
                .setContentText("正在下载APP..........")
                .setSmallIcon(android.R.drawable.ic_btn_speak_now);

        // Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        for (incr = 0; incr <= 100; incr += 15) {
                            mBuilder.setProgress(100, incr, false);//更新下载的进度
                            mManager .notify(2, mBuilder.build());//发送通知，每次更新都要发送通知，来改变UI
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(1 * 1000);
                            } catch (InterruptedException e) {
                            }
                        }
                        //设置PendingIntent，为了点击跳转
                        Intent intent = new Intent(NotificationDemo.this, NotificationDemo.class);
                        PendingIntent padingIntent = PendingIntent.getActivity(NotificationDemo.this, 0, intent, 0);

                        mBuilder.setContentText("Download complete")//下载完成
                                .setProgress(0, 0, false)
                                .setContentIntent(padingIntent);    //移除进度条
                       mManager.notify(2, mBuilder.build());


                    }
                }).start();
    }
}
