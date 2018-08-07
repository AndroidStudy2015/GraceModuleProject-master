package com.yingdou.www.toucheventtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yingdou.www.toucheventtest.R;
import com.yingdou.www.toucheventtest.okshuxinview.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class OkShuaXinActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_shua_xin);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dataList.add("NI-HAO-YA:" + i);
        }
        MyAdapter adapter = new MyAdapter(this, dataList);
        mRecyclerView.setAdapter(adapter);


        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshFinish(9000);
            }
        });
    }
}
