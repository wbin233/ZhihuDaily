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
import com.example.dell_pc.wbin_zhihudaily.bean.TopStoryEntity;
import com.example.dell_pc.wbin_zhihudaily.ui.fragment.MainFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wbin on 2016/8/22.
 */
public class MainAdapter extends RecyclerView.Adapter {
    private static int HEADER = -1;
    private static int WITH_TITLE = 0;
    private static int WITHOUT_TITLE = 1;

    List<StoryEntity> list;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_banner, parent, false);
            return new HeaderHolder(view);
        } else if (viewType == WITHOUT_TITLE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
            return new MainHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_with_title, parent, false);
            return new MainHolderWithTitle(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StoryEntity item = list.get(position);
        if (holder instanceof HeaderHolder) {
            ((HeaderHolder) holder).banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            List<String> titleList = new ArrayList<>();
            List<String> imgList = new ArrayList<>();
            for (TopStoryEntity entity : MainFragment.topStoryEntities) {
                titleList.add(entity.getTitle());
                imgList.add(entity.getImage());
            }
            ((HeaderHolder) holder).banner.setBannerTitleList(titleList);
            ((HeaderHolder) holder).banner.setImages(imgList);
        } else if (holder instanceof MainHolderWithTitle) {
            ((MainHolderWithTitle) holder).titleTv.setText(item.getDate());
            ((MainHolderWithTitle) holder).textView.setText(item.getTitle());
            Glide.with(holder.itemView.getContext()).load(item.getImages().get(0)).into(((MainHolderWithTitle) holder).imgView);
        } else {
            ((MainHolder) holder).textView.setText(item.getTitle());
            Glide.with(holder.itemView.getContext()).load(item.getImages().get(0)).into(((MainHolder) holder).imgView);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else if (list.get(position).isTop()) {
            return WITH_TITLE;
        } else {
            return WITHOUT_TITLE;
        }
    }

    public void setList(List<StoryEntity> list) {
        //list.get(0).setTop(true);
        this.list = list;
        this.list.add(0, new StoryEntity());
        notifyDataSetChanged();
    }

    public void appendList(List<StoryEntity> list) {
        list.get(0).setTop(true);
        if (this.list == null) {
            setList(list);
        } else {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
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

    class MainHolderWithTitle extends MainHolder {
        @Bind(R.id.card_item_title)
        TextView titleTv;

        public MainHolderWithTitle(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.banner)
        Banner banner;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
