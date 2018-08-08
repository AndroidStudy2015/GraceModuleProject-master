package com.yingdou.www.toucheventtest.okokok.useDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
//        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));

        wrapperAdapter = new WrapperAdapter(mRecyclerView, new InWrapperAdapter(this, dataList));
//        wrapperAdapter.setLoadMoreView(new ClassicFooter111(this));
        mRecyclerView.setAdapter(wrapperAdapter);


        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        count=0;

                        dataList.clear();
                        setData0();
                        wrapperAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshFinish(0);
                        wrapperAdapter.resetLodingMore();
                    }
                },3000);



            }
        });

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
                            wrapperAdapter.setLoadMoreFinish();
                        } else {
                            wrapperAdapter.setLoadNoMore();

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
        for (int i = 1; i <= 10; i++) {
            dataList.add("NI-HAO-YA:" + i);
        }
    }
}
