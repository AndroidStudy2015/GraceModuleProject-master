package com.yingdou.www.toucheventtest.okokok.useDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yingdou.www.toucheventtest.R;
import com.yingdou.www.toucheventtest.okokok.loadmoreview.WrapperAdapter;
import com.yingdou.www.toucheventtest.okokok.refresh.RefreshLayout;

import java.util.ArrayList;

public class WrapperAdapterActivity extends AppCompatActivity {

    private ArrayList<String> dataList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layout;
    private RefreshLayout mRefreshLayout;
    private WrapperAdapter wrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapper_adapter);

        dataList = new ArrayList<>();
        setData0();


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));

        wrapperAdapter = new WrapperAdapter(mRecyclerView, new InWrapperAdapter(this, dataList));
//        wrapperAdapter.setLoadMoreView(new ClassicFooter111(this));//如果不适用默认的头布局，可以通过此方法添加新的头布局
        mRecyclerView.setAdapter(wrapperAdapter);


//        刷新

        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        count=0;

                        dataList.clear();
                        setData();
                        wrapperAdapter.notifyDataSetChanged();



                        mRefreshLayout.setRefreshFinish(0);//关闭刷新
                        wrapperAdapter.resetLodingMore();//★★★★★★★★★★★★★★★★★★★★★刷新时，重新设置loadMore，因为可能都用过了setoadNoMore，禁用了加载更多
                    }
                },3000);



            }
        });

//        加载
        wrapperAdapter.setOnLoadMoreListener(new WrapperAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                Toast.makeText(mRecyclerView.getContext(), "loamore", Toast.LENGTH_SHORT).show();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (count < 4) {
                            setData();
                            wrapperAdapter.notifyDataSetChanged();
                            wrapperAdapter.setLoadMoreFinish();//设置加载完成
                        } else {
                            wrapperAdapter.setLoadNoMore();//设置没有更多数据，同时：禁用了加载更多，所以记得刷新时记得wrapperAdapter.resetLodingMore()

                        }
                    }
                }, 1000);
            }
        });

    }

    int count = 0;

    private void setData() {
        count++;
        for (int i = 1; i <= 5; i++) {
            dataList.add("NI-HAO-YA:" + i);
        }
    }

    private void setData0() {
        count++;
        for (int i = 1; i <= 17; i++) {
            dataList.add("NI-HAO-YA:" + i);
        }
    }
}
