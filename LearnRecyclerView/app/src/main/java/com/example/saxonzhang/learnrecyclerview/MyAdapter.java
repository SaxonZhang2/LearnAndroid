package com.example.saxonzhang.learnrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by saxonzhang on 2016/1/6.
 */
class MyAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(new TextView(viewGroup.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder vh = (MyViewHolder) viewHolder;
        vh.getTv().setText(data[i]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    private String [] data = new String[]{
            "apple",
            "banana",
            "pear",
            "orange",
            "watermelon",

            "apple",
            "banana",
            "pear",
            "orange",
            "watermelon",

            "apple",
            "banana",
            "pear",
            "orange",
            "watermelon",

            "apple",
            "banana",
            "pear",
            "orange",
            "watermelon",

            "apple",
            "banana",
            "pear",
            "orange",
            "watermelon",

            "apple",
            "banana",
            "pear",
            "orange",
            "watermelon",

            "apple",
            "banana",
            "pear",
            "orange",
            "watermelon",

            "apple",
            "banana",
            "pear",
            "orange",
            "watermelon",
    };
}
