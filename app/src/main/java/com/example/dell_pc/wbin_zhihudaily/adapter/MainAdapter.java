package com.example.dell_pc.wbin_zhihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell_pc.wbin_zhihudaily.R;
import com.example.dell_pc.wbin_zhihudaily.bean.StoryEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wbin on 2016/8/22.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
    List<StoryEntity> list;

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        StoryEntity item = list.get(position);
        holder.textView.setText(item.getTitle());
        Glide.with(holder.itemView.getContext()).load(item.getImages().get(0)).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<StoryEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void appendList(List<StoryEntity> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class MainHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_item_tv)
        TextView textView;
        @Bind(R.id.card_item_img)
        ImageView imgView;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
