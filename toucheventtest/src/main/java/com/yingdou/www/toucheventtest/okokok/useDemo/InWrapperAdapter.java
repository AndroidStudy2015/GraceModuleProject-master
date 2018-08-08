package com.yingdou.www.toucheventtest.okokok.useDemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yingdou.www.toucheventtest.R;

import java.util.List;

public class InWrapperAdapter extends RecyclerView.Adapter<InWrapperAdapter.MyVH> {

    private List<String> mDataList;
    private Context mContext;

    public InWrapperAdapter(Context context, List<String> dataList) {
        mDataList = dataList;
        mContext = context;
    }


    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_inwrapper, parent, false);


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

    boolean mHasNoMore = false;

    public void setNoMore(boolean hasNoMore) {
        mHasNoMore = hasNoMore;
    }

    class MyVH extends RecyclerView.ViewHolder {

        private final TextView tv;

        public MyVH(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
        }
    }

}
