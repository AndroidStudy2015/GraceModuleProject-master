package com.yingdou.www.toucheventtest.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yingdou.www.toucheventtest.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyVH> {

    private List<String> mDataList;
    private Context mContext;

    public MyAdapter(Context context, List<String> dataList) {
        mDataList = dataList;
        mContext = context;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new MyVH(view);
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {
        holder.tv.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyVH extends RecyclerView.ViewHolder {

        private final TextView tv;

        public MyVH(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
        }
    }

}
