package com.yingdou.www.toucheventtest.customviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class MyMyLinearLayoutOut extends LinearLayout {

    private int mLastY;
    private View scrollView;
    private double mLastXIntercept;
    private double mLastYIntercept;
    private int mLastX;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private View header;
    private int mHeaderHeight;
    private View footer;
    private int mFooterHeight;
    private int mHeaderMaxPullDownHeight;
    private int mFooterMaxPullUpHeight;
    private int mRootHeight;
    private int duration = 300;

    public MyMyLinearLayoutOut(Context context) {
        super(context);
        init(context);
    }

    public MyMyLinearLayoutOut(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                header = getChildAt(0);
                scrollView = getChildAt(1);
                footer = getChildAt(2);

                mFooterHeight = footer.getMeasuredHeight();
                mHeaderHeight = header.getMeasuredHeight();


                mRootHeight = getMeasuredHeight();

//                //如果他设置了match_parent的话，把内部的scrollview强制设为与这个根布局相同的高度
                ViewGroup.LayoutParams layoutParams = scrollView.getLayoutParams();
                layoutParams.height = mRootHeight;
                scrollView.setLayoutParams(layoutParams);


                mHeaderMaxPullDownHeight = (int) (mHeaderHeight * 1.5);
                mFooterMaxPullUpHeight = (int) (mFooterHeight * 1.5);

//                布局向上移动mHeaderHeight，把header隐藏
                scrollTo(0, mHeaderHeight);
            }
        }, 100);


    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.e("qqq", "mRootHeight:" + mRootHeight + "   scrollHeigt:" + scrollView.getMeasuredHeight());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //不拦截down事件
                intercepted = false;
                //如果mScroller缓冲滑动没结束，那么马上结束
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = (int) (x - mLastXIntercept);
                int deltaY = (int) (y - mLastYIntercept);


                if (deltaY > 0 && !scrollView.canScrollVertically(-1)
                        || deltaY < 0 && !scrollView.canScrollVertically(1)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;

        return intercepted;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);

        int y = (int) ev.getY();
//        if (STATE != DEFAULT) {
//            return true;
//        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int dY = y - mLastY;
                Log.e("ccc", "scrollY:" + getScrollY() + "   dy:" + dY + "   mFooterMaxPullUpHeight:" + mFooterMaxPullUpHeight);

                //控制根布局的上拉、下拉边界

                //当头部下拉距离超过mHeaderMaxPullDownHeight时，就直接滑到- mHeaderMaxPullDownHeight
                if (getScrollY() - dY < -mHeaderMaxPullDownHeight) {
                    scrollTo(0, -mHeaderMaxPullDownHeight);
                    //当脚部上拉距离超过mFooterMaxPullUpHeight时，就直接滑到mFooterMaxPullUpHeight

                } else if (getScrollY() - dY > mFooterMaxPullUpHeight + mHeaderMaxPullDownHeight) {
                    scrollTo(0, mFooterMaxPullUpHeight + mHeaderMaxPullDownHeight);
                    //边界以内的话，按照dY去滑动整个根布局
                } else {
                    scrollBy(0, (int) (-dY * 0.3));
                    if (getScrollY()<mHeaderHeight){
                        STATE = IS_REFRESHING;

                    }

                    if (STATE==IS_REFRESHING&&getScrollY()>mHeaderHeight){
                        scrollTo(0,mHeaderHeight);
//                        STATE=DEFAULT;
                    }


//                    if (getScrollY()>=mHeaderHeight){
//                        STATE = IS_LOADING_MORE;
//
//                    }
//
//                    if (STATE==IS_REFRESHING&&getScrollY()>mHeaderHeight){
//                        scrollTo(0,mHeaderHeight);
//                        STATE=DEFAULT;
//                    }

                }

                break;


            case MotionEvent.ACTION_UP:
                Log.e("ccc", "up:getScrollY" + getScrollY() + "");
                if (getScrollY() < mHeaderHeight) {
                    STATE = IS_REFRESHING;
                    Log.e("state:", STATE + "IS_REFRESHING");

                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            STATE = DEFAULT;
                            Log.e("state:", STATE + "DEFAULT");
                        }
                    }, duration);
                    smoothScrollBy(0, mHeaderHeight - getScrollY());
                }

                if (getScrollY() > mHeaderHeight + mFooterHeight) {
                    STATE = IS_LOADING_MORE;

                    smoothScrollBy(0, mHeaderHeight - getScrollY());
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            STATE = DEFAULT;

                        }
                    }, duration);
                }
                break;

            default:

                break;
        }

        mLastY = y;

        return true;
    }

    int DEFAULT = 0;
    int IS_REFRESHING = 1;
    int IS_LOADING_MORE = 2;
    int STATE = DEFAULT;

    private void smoothScrollBy(int dx, int dy) {
        Log.e("qqq", "smoothScrollBy" + getScrollY() + "  dy" + dy);
        mScroller.startScroll(0, getScrollY(), 0, dy, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
