package com.example.saxonzhang.learnrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by saxonzhang on 2016/1/6.
 */
class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView tv;
    public MyViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView;
    }

    public TextView getTv() {
        return tv;
    }
}
