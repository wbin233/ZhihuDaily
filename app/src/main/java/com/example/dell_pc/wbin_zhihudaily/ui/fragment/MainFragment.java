package com.example.dell_pc.wbin_zhihudaily.ui.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell_pc.wbin_zhihudaily.R;
import com.example.dell_pc.wbin_zhihudaily.adapter.MainAdapter;
import com.example.dell_pc.wbin_zhihudaily.bean.NewsEntity;
import com.example.dell_pc.wbin_zhihudaily.network.Network;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wbin on 2016/8/22.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    MainAdapter adapter = new MainAdapter();

    Subscriber subscriber = new Subscriber<NewsEntity>() {
        @Override
        public void onCompleted() {
            Toast.makeText(getActivity(), "完成!!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(NewsEntity newsEntity) {
            adapter.setList(newsEntity.getStories());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setRefreshing(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        loadData();
        return view;
    }

    private void loadData() {
        Network.getZhihuApi()
                .getLatestNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
