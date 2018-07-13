package com.grace.www.videoplayer;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;


/**
 * 播放View+控制器+全屏播放Dialog（共享一个播放器）
 * 源自七牛云播放器：https://developer.qiniu.com/pili/sdk/1210/the-android-client-sdk
 */
public class MyVideoPlayerActivity extends AppCompatActivity
//        implements
//        PLOnPreparedListener,
//        PLOnInfoListener,
//        PLOnCompletionListener,
//        PLOnVideoSizeChangedListener,
//        PLOnErrorListener
{

    private PLVideoTextureView mVideoView;
    private String videoPath = "http://demo-videos.qnsdk.com/movies/qiniu.mp4";
    private ImageView coverImage;
    private ImageButton stopPlayImage;
    private LinearLayout loadingView;
    private ImageButton fullScreenImage;
    private MediaController mediaController;
    private FrameLayout flPlayer;
    private FrameLayout flPlayerPlaceholder;
    private MyDialoge myDialoge;
    private LinearLayout llConttrol;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video_player);
        initView();
        setupVideoPlayer();
    }

    private void initView() {
        flPlayerPlaceholder = findViewById(R.id.fl_player_placeholder);
        myDialoge = new MyDialoge(MyVideoPlayerActivity.this, R.style.dialog_full_screen);

    }


    private void setupVideoPlayer() {

        initPlayerView();
        setPlayerListener();

        mVideoView.setAVOptions(Utils.createAVOptions());
        mVideoView.setBufferingIndicator(loadingView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
        mVideoView.setLooping(true);


    }

    private void initPlayerView() {
        flPlayer = findViewById(R.id.fl_player);
        mVideoView = findViewById(R.id.video_texture_view);
        coverImage = findViewById(R.id.cover_image);
        stopPlayImage = findViewById(R.id.cover_stop_play);
        loadingView = findViewById(R.id.loading_view);
        fullScreenImage = findViewById(R.id.full_screen_image);
        mediaController = findViewById(R.id.media_controller);
        llConttrol = findViewById(R.id.ll_control);
        mGestureDetector = new GestureDetector(new GestureControllerListener(MyVideoPlayerActivity.this));

    }

    public void turnFullScreen() {
//        1.变为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        2.小屏幕的占位Framelayout移除播放器
        flPlayerPlaceholder.removeView(flPlayer);

//        3.全屏幕Dialog添加播放器
        myDialoge.getDialogRootView().addView(flPlayer);
        restartCurVideoView();


    }

    public void turnSmallScreen() {
//        1.变为竖直屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        2.全屏幕Dialog删除播放器
        myDialoge.getDialogRootView().removeView(flPlayer);

//        3.小屏幕的占位Framelayout添加播放器
        flPlayerPlaceholder.addView(flPlayer);
        restartCurVideoView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        } else {
        }
    }

    public void startCurVideoView() {
        mVideoView.setVideoPath(videoPath);

        mVideoView.start();
        loadingView.setVisibility(View.VISIBLE);
        stopPlayImage.setVisibility(View.GONE);
    }

    public void restartCurVideoView() {
        mVideoView.start();
        stopPlayImage.setVisibility(View.GONE);
    }

    public boolean isCurVideoPlaying() {
        return mVideoView.isPlaying();
    }

    public boolean needBackstagePlay() {
//        return BACKSTAGE_PLAY_TAG.equals(mCurViewHolder.videoView.getTag());
        return false;
    }

    public void pauseCurVideoView() {
        mVideoView.pause();
        loadingView.setVisibility(View.GONE);
    }


    private void resetConfig() {
        mVideoView.setRotation(0);
        mVideoView.setMirror(false);
        mVideoView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
    }

    public void stopCurVideoView() {
        resetConfig();
        mVideoView.stopPlayback();
        loadingView.setVisibility(View.GONE);
        coverImage.setVisibility(View.VISIBLE);
        stopPlayImage.setVisibility(View.VISIBLE);
    }

    private void setPlayerListener() {
//        mVideoView.setOnPreparedListener(this);
//        mVideoView.setOnInfoListener(this);
//        mVideoView.setOnCompletionListener(this);
//        mVideoView.setOnVideoSizeChangedListener(this);
//        mVideoView.setOnErrorListener(this);


        mVideoView.setOnInfoListener(new PLOnInfoListener() {
            @Override
            public void onInfo(int i, int i1) {
                if (i == PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START) {
                    coverImage.setVisibility(View.GONE);
                    stopPlayImage.setVisibility(View.GONE);
                    mediaController.hide();
                }
            }
        });

        mediaController.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO: 2018/7/13 加上这句话 实现调节音量 、屏幕暗度，但需要把controller_stop_play的尺寸设置为不是match_aprent
//                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });


        coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopCurVideoView();
                startCurVideoView();
            }
        });

        fullScreenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDialoge.isShowing()) {
                    myDialoge.dismiss();
                } else {
                    myDialoge.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (myDialoge.isShowing()) {
            myDialoge.dismiss();
        } else {
            super.onBackPressed();
        }

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();

    }
}
