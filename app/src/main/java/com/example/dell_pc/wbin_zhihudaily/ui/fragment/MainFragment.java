package com.example.dell_pc.wbin_zhihudaily.ui.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell_pc.wbin_zhihudaily.R;
import com.example.dell_pc.wbin_zhihudaily.adapter.MainAdapter;
import com.example.dell_pc.wbin_zhihudaily.bean.NewsEntity;
import com.example.dell_pc.wbin_zhihudaily.bean.StoryEntity;
import com.example.dell_pc.wbin_zhihudaily.bean.TopStoryEntity;
import com.example.dell_pc.wbin_zhihudaily.network.Network;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wbin on 2016/8/22.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    MainAdapter adapter = new MainAdapter();
    public static ArrayList<TopStoryEntity> topStoryEntities;   //热门新闻
    boolean state = true;     //状态，为true表示获取今日要闻或刷新，为false表示获取往期消息

    Subscriber subscriber = new Subscriber<NewsEntity>() {
        @Override
        public void onCompleted() {
            Toast.makeText(getActivity(), "完成!!", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onNext(NewsEntity newsEntity) {
            Log.e(MainFragment.class.toString(), "on next!!");
            String date = newsEntity.getDate();
            for (StoryEntity storyEntity : newsEntity.getStories()) {
                storyEntity.setDate(date);
            }
            if (state) {
                topStoryEntities = newsEntity.getTop_stories();
                adapter.setList(newsEntity.getStories());
            } else {
                adapter.appendList(newsEntity.getStories());
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        state = true;
        loadData();
        return view;
    }

    private void loadData() {
        Network.getZhihuApi()
                .getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void loadHistoryData() {
        Log.e(MainFragment.class.toString(), "loadHistoryData!!!");
        state = false;
        Network.getZhihuApi2()
                .getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void onRefresh() {
        Log.e(MainFragment.class.toString(), "刷新！！！！！！！");
        state = true;
        loadHistoryData();
    }
}
