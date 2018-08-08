package com.yingdou.www.toucheventtest.okokok.loadmoreview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yingdou.www.toucheventtest.okokok.loadmoreview.footer.BaseFooter;
import com.yingdou.www.toucheventtest.okokok.loadmoreview.footer.ClassicFooter;

public class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SwipeToLoadHelper mLoadMoreHelper;
    private RecyclerView.Adapter mAdapter;
    private Context mContext;
    private int ITEM_TYPE_LOAD_MORE = 1987;
    private WrapperHolder mWrapperHolder;

    public BaseFooter getmLoadMoreView() {
        return mLoadMoreView;
    }

    private BaseFooter mLoadMoreView;

    public void setLoadMoreView(BaseFooter loadMoreView) {
        this.mLoadMoreView = loadMoreView;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoadMoreView.setLayoutParams(params);//给new出来的view设置宽高

    }


    public WrapperAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mContext = recyclerView.getContext();

        setLoadMoreView(new ClassicFooter(mContext));//设置了一个默认的《加载更多布局》，外界通过setLoadMoreView可以重新设置脚布局
        mLoadMoreHelper = new SwipeToLoadHelper(recyclerView,mLoadMoreView);//这里主要处理滑动到底部，触发加载更多的逻辑

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            //如果是脚布局
            mWrapperHolder = new WrapperHolder(mLoadMoreView);
            return mWrapperHolder;
        } else {
            //其余都是正常布局，交给传递进来的mAdapter去处理
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == getItemCount() - 1) {
            //如果当前position是最后一个，不作处理
        } else {
            //如果是正常布局，交给传递进来的mAdapter去处理
            mAdapter.onBindViewHolder(holder, position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        // 当显示"加载更多"条目, 并且位置是最后一个时, wrapper进行拦截
        if (position == mAdapter.getItemCount()) {
            return ITEM_TYPE_LOAD_MORE;// 注意要避免和原生adapter返回值重复，我取的是1987
        }
        // 其他情况交给原生adapter处理
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    class WrapperHolder extends RecyclerView.ViewHolder {
        WrapperHolder(View itemView) {
            super(itemView);
        }

    }

//*****************//*****************//**********控制UI*******//*****************//*****************//*****************

    /**
     * 加载完成时候调用，改变脚布局UI
     */
    public void setLoadMoreFinish() {

        resetLodingMore();
        mLoadMoreHelper.setLodingMore(false);
    }

    /**
     * 加载完成，没有更多数据时调用，改变脚布局UI
     */
    public void setLoadNoMore() {

        mLoadMoreView.onLoadNoMore();
        //标志LodingMore设为true，禁用加载更多，表示没有更多数据了，防止手指滑动后，再次加载更多
        mLoadMoreHelper.setLodingMore(true);

    }

    /**
     * 开始加载，改变脚布局UI
     */
    private void setLoadingMore() {
        //标志LodingMore设为true，禁用加载更多，表示正在加载，防止手指滑动后，多次加载更多
        mLoadMoreHelper.setLodingMore(true);

        mLoadMoreView.onLoadingMore();

    }

    /**
     * 记得每次刷新时候，调用这个方法，激活加载更多
     */
    public void resetLodingMore() {
//        启用加载更多
        mLoadMoreHelper.setLodingMore(false);
    }

//*****************//*****************//*****回调************//*****************//*****************//*****************


    /**
     * ***************************外界加载更多的回调**************************
     *
     * @param onLoadMoreListener
     */
    public void setOnLoadMoreListener(final OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
        mLoadMoreHelper.setLoadMoreHelperListener(new SwipeToLoadHelper.LoadMoreHelperListener() {
            @Override
            public void onLoadHelper() {
                setLoadingMore();
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        });
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMore();

    }
}
