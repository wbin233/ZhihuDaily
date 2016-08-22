package com.example.dell_pc.wbin_zhihudaily.network.api;

import com.example.dell_pc.wbin_zhihudaily.bean.NewsEntity;
import com.example.dell_pc.wbin_zhihudaily.bean.StoryDetailsEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wbin on 2016/8/22.
 */
public interface ZhihuApi {

    /**
     * 获取当日最新消息
     *
     * @return
     */
    @GET("api/4/news/latest")
    Observable<NewsEntity> getLatestNews();

    /**
     * 获取过往信息
     *
     * @param id 日期；如果需要查询 11 月 18 日的消息，id应为 20131119; id需大于等于20130520
     * @return
     */
    @GET("api/4/news/before/{id}")
    Observable<NewsEntity> getBeforeNews(@Path("id") String id);

    /**
     * 获取消息详细内容
     *
     * @param id 消息id
     * @return
     */
    @GET("api/4/news/{id}")
    Observable<StoryDetailsEntity> getNewsDetails(@Path("id") int id);
}
