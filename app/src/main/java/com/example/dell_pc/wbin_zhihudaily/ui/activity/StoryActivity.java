package com.example.dell_pc.wbin_zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.dell_pc.wbin_zhihudaily.R;
import com.example.dell_pc.wbin_zhihudaily.bean.StoryDetailsEntity;
import com.example.dell_pc.wbin_zhihudaily.bean.StoryEntity;
import com.example.dell_pc.wbin_zhihudaily.network.Network;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wbin on 2016/8/24.
 */
public class StoryActivity extends AppCompatActivity {

    @Bind(R.id.story_wv)
    WebView webView;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    StoryEntity storyEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");

        storyEntity = getIntent().getParcelableExtra(StoryEntity.class.toString());
        load();
    }

    private void load() {
        Network.getZhihuApi()
                .getNewsDetails(storyEntity.getId())
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<StoryDetailsEntity, String>() {
                    @Override
                    public String call(StoryDetailsEntity storyDetailsEntity) {
                        String oriStr = storyDetailsEntity.getBody();
                        oriStr = oriStr.replace("<div class=\"img-place-holder\">",
                                "<img style=\"height:200px;width:100%\" src=\"" + storyDetailsEntity.getImage() + "\"/>");
                        List<String> cssList = storyDetailsEntity.getCss();
                        StringBuilder htmlString = new StringBuilder("<html><head>");
                        for (String css : cssList) {
                            htmlString.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"" + css + "\">");
                        }
                        htmlString.append("</head><body>");
                        htmlString.append("<style>img{max-width:340px !important;}</style>");
                        htmlString.append(oriStr);
                        htmlString.append("</body></html>");
                        return htmlString.toString();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(StoryActivity.this, "completed!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(StoryActivity.class.toString(), e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        toolbar.setTitle(storyEntity.getTitle());
                        webView.loadData(s, "text/html; charset=UTF-8", null);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.story_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                Toast.makeText(StoryActivity.this, "开发中...", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
