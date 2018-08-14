package com.yingdou.www.toucheventtest.okokok.refresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class RefreshLayout extends LinearLayout {
    private Context mContext;
    private View mHeader;
    private View mBody;
    private int mRootHeight;
    private int mHeaderHeight;
    private int mHeaderPullDownMaxHeight;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int smoothScrollDuration = 300;
    int mRefreshFinishDelay = 3000;
    private IHeaderControl mIHeaderControl;
    int STATE_DEFAULT = 0;
    int STATE_PULL_DOWN_REFRESH = 1;
    int STATE_RELEASE_REFRESH = 2;
    int STATE_REFRESHING = 3;
    int STATE_REFRESH_FINISH = 4;
    int STATE = STATE_DEFAULT;
    int mLastX;
    int mLastY;
    /**
     * 当处于刷新ing时候，拦截事件，不允许内部的view滚动
     * false：允许内部view滚动
     * true: 不允许内部view滚动
     */
    boolean forbiddenChildViewScrollIfIsRefreshing = true;
    /**
     * 当处于刷新ing时候，不允许自身滚动
     * false：允许自身滚动
     * true: 不允许自身滚动
     */
    boolean forbiddenSelfScrollIfIsRefreshing = false;

    public RefreshLayout(Context context) {
        this(context, null);


    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new Scroller(mContext);


        //延时100毫秒，不然得到的宽高都为0，而且getChildCount也为0
        postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, 100);

    }

    private void init() {
        mVelocityTracker = VelocityTracker.obtain();

        int childCount = getChildCount();
        if (childCount > 1) {
            mHeader = getChildAt(0);

            try {
                mIHeaderControl = (IHeaderControl) mHeader;

            } catch (ClassCastException e) {
                throw new RuntimeException("RefreshLayout布局里的第一个子View，必须是一个头布局，" +
                        "这个头布局必须继承自BaseHeader，头布局可以使用默认提供的ClassicHeader，" +
                        "也可以仿照ClassicHeader去自定义");
            }

            mBody = getChildAt(1);


            mRootHeight = getMeasuredHeight();
            mHeaderHeight = mHeader.getMeasuredHeight();
            mHeaderPullDownMaxHeight = mHeaderHeight * 2;
            setBodyHeightIfMachPrent();
            //布局向上移动mHeaderHeight，把header隐藏
            scrollTo(0, mHeaderHeight);
        } else {
            throw new RuntimeException("RefreshLayout布局,必须包含两个子View，第一个子View为刷新头布局（必须继承自BaseHeader）" +
                    "第二个子View，一般是可以滑动的RecyclerView或者ScrollView,不能滑动的view也是可以的，但一般不这么做");
        }


    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        boolean intercepted = false;

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
//                //如果mScroller缓冲滑动没结束，那么马上结束
//                if (!mScroller.isFinished()) {
//                    mScroller.abortAnimation();
//                    intercepted = true;
//                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (deltaY > 0 && !mBody.canScrollVertically(-1)//开始下拉刷新
//                        || deltaY < 0 && !mBody.canScrollVertically(1)//开始加载更多
                        ) {
                    intercepted = true;
                } else if (STATE == STATE_REFRESHING && forbiddenChildViewScrollIfIsRefreshing) {
                    //当处于刷新ing时候，拦截事件，不允许内部的view滚动
                    intercepted = true;

                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return intercepted;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.e("layout", "onTouchEvent");

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                if (!mScroller.isFinished()) {
//                    mScroller.abortAnimation();
//                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;


                if (STATE == STATE_REFRESHING && forbiddenSelfScrollIfIsRefreshing) {
                    return true;
                }
                int scrollY = getScrollY();


                if (scrollY - deltaY < -mHeaderPullDownMaxHeight) {
                    scrollTo(0, -mHeaderPullDownMaxHeight);
                } else if (scrollY - deltaY > mHeaderHeight) {
                    scrollTo(0, mHeaderHeight);

                } else {
                    scrollBy(0, (int) (-deltaY * 0.9));


                    if (STATE != STATE_REFRESHING) {//如果正在刷新，那么不走下面的回调
                        if (scrollY >= 0 && scrollY <= mHeaderHeight) {
                            STATE = STATE_PULL_DOWN_REFRESH;
                            float percent = (mHeaderHeight - scrollY) * 1.0f / mHeaderHeight;
                            mIHeaderControl.onPullDownRefresh(mHeaderHeight - scrollY, mHeaderHeight, percent);

//                            Log.e("refresh", "STATA:" + STATE + "   scrollY:" + scrollY + "   mHeaderHeight:" + mHeaderHeight);
                        } else if (scrollY < 0 && scrollY > -mHeaderPullDownMaxHeight) {

                            STATE = STATE_RELEASE_REFRESH;
                            float percent = (mHeaderHeight - scrollY) * 1.0f / mHeaderHeight;
                            mIHeaderControl.onRealseRefresh(mHeaderHeight - scrollY, mHeaderHeight, percent);
//                            Log.e("refresh", "STATA:" + STATE + "   scrollY:" + scrollY + "   mHeaderHeight:" + mHeaderHeight);

                        }
                    }

                }

//                Log.e("layout---", "getScrollY():" + getScrollY() +
//                        " deltaY:" + deltaY + "   mHeaderPullDownMaxHeight:" + mHeaderPullDownMaxHeight);

                break;
            case MotionEvent.ACTION_UP:

                if (STATE == STATE_RELEASE_REFRESH) {
                    STATE = STATE_REFRESHING;
                    smoothScrollBy(0, 0 - getScrollY());
                    float percent = (mHeaderHeight) * 1.0f / mHeaderHeight;
                    mIHeaderControl.onRefreshing(mHeaderHeight, mHeaderHeight, percent);
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onRefresh();
                    }
//                    Log.e("refresh", "STATA:" + STATE + "   scrollY:" + getScrollY() + "   mHeaderHeight:" + mHeaderHeight);


                }

                if (STATE == STATE_REFRESHING) {
                    smoothScrollBy(0, 0 - getScrollY());
                }
                if (STATE == STATE_PULL_DOWN_REFRESH) {
                    STATE = STATE_DEFAULT;
                    smoothScrollBy(0, mHeaderHeight - getScrollY());
//                    Log.e("refresh", "STATA:" + STATE + "   scrollY:" + getScrollY() + "   mHeaderHeight:" + mHeaderHeight);

                }


                break;
            default:
                break;


        }

        mLastY = y;

        return true;
    }

    private void setBodyHeightIfMachPrent() {
        ViewGroup.LayoutParams lp = mBody.getLayoutParams();
        Log.e("qwe", lp.height + "  " + lp.width);
        if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            //假如第二个子View高度设置为MATCH_PARENT，那么这里把RefreshLayout的高度mRootHeight赋值给子View
            //为什么要这么做呢，因为RefreshLayout里有两个View，头布局和可滑动的View，直接给第二个view高度写MATCH_PARENT，
            // 他等于 RefreshLayout的高度-头布局高度，这样不是我们想要的，所以这里强行设置为RefreshLayout的高度
            lp.height = mRootHeight;
            mBody.setLayoutParams(lp);
        }

    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(0, getScrollY(), 0, dy, smoothScrollDuration);
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


    /**
     * 外界调用这个方法，结束刷新
     *
     * @param delay 多少毫秒后结束刷新
     */

    public void setRefreshFinish(int delay) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                STATE = STATE_REFRESH_FINISH;
                mIHeaderControl.onRefreshFinish();
                smoothScrollBy(0, mHeaderHeight - getScrollY());
//                Log.e("refresh", "STATA:" + STATE + "   scrollY:" + getScrollY() + "   mHeaderHeight:" + mHeaderHeight);
            }
        }, delay);
    }


    /**
     * 如果要自定义一个header，这个header必须实现IHeaderControl接口,由于写了BaseHeader，BaseHeader实现了IHeaderControl接口
     * 所以如果要自定义header的话，只需要继承BaseHeader就行了
     */
    public interface IHeaderControl {


        void onPullDownRefresh(int curPullDownHeight, int headerHeight, float percent);

        void onRealseRefresh(int curPullDownHeight, int headerHeight, float percent);

        void onRefreshing(int curPullDownHeight, int headerHeight, float percent);

        void onRefreshFinish();

    }

    public void setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }

    private OnRefreshListener mOnRefreshListener;

    public interface OnRefreshListener {
        void onRefresh();
    }
}
