package com.yingdou.www.toucheventtest.okokok.loadmoreview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yingdou.www.toucheventtest.okokok.loadmoreview.footer.BaseFooter;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * 上滑加载更多操作辅助类
 */

public class SwipeToLoadHelper extends RecyclerView.OnScrollListener {
    private final RecyclerView.LayoutManager mLayoutManager;
    BaseFooter mLoadMoreView;

    public void setLodingMore(boolean lodingMore) {
        isLodingMore = lodingMore;
    }

    boolean isLodingMore = false;

    public SwipeToLoadHelper(RecyclerView recyclerView, BaseFooter loadMoreView) {

        mLayoutManager = recyclerView.getLayoutManager();
        mLoadMoreView = loadMoreView;
        if (mLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) mLayoutManager;

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int spanCount = gridLayoutManager.getSpanCount();

                    if (position == gridLayoutManager.getItemCount() - 1) {
                        return spanCount;
                    } else {
                        return 1;

                    }
                }
            });

        }
        // 将OnScrollListener设置RecyclerView
        recyclerView.addOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        if (mLayoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mLayoutManager;

            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            if (firstVisibleItemPosition == 0) {
                mLoadMoreView.onLoadNoMore();
            } else if (!isLodingMore && newState == SCROLL_STATE_IDLE && lastVisibleItemPosition == mLayoutManager.getItemCount() - 1) {

                if (mLoadMoreHelperListener != null) {
                    mLoadMoreHelperListener.onLoadHelper();
                }

            }

        }
        if (mLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) mLayoutManager;
            int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
            int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
            if (firstVisibleItemPosition == 0) {
                mLoadMoreView.onLoadNoMore();
            } else if (!isLodingMore && newState == SCROLL_STATE_IDLE && lastVisibleItemPosition == mLayoutManager.getItemCount() - 1) {

                if (mLoadMoreHelperListener != null) {
                    mLoadMoreHelperListener.onLoadHelper();
                }

            }
        }


    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    public void setLoadMoreHelperListener(LoadMoreHelperListener listener) {
        this.mLoadMoreHelperListener = listener;
    }

    LoadMoreHelperListener mLoadMoreHelperListener;

    public interface LoadMoreHelperListener {
        void onLoadHelper();
    }


}
