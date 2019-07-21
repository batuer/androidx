package com.gusi.androidx.model;

import android.util.Log;

import androidx.core.util.Pair;

import com.gusi.androidx.model.http.Api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;

/**
 * @Author ylw 2018/6/21 14:20
 */
public final class DataManager implements IData, Api {
    private Api mApi;

    public DataManager() {}

    public DataManager(Api api) {
        mApi = api;
    }

    @Override
    public Flowable<List<Pair<String, String>>> getRegistration(String url) {
        return Flowable.fromCallable(() -> {
            List<Pair<String, String>> dataIdPairs = new ArrayList(0);
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("xmgs_tab");
            if (elements != null && elements.first() != null) {
                Element first = elements.first();
                Elements dataIdElements = first.getElementsByAttribute("data-id");
                for (Element element : dataIdElements) {
                    String text = element.text();
                    dataIdPairs.add(new Pair<>(text, element.attr("data-id")));
                }
            }
            return dataIdPairs;
        });
    }

    @Override
    public Flowable<Object> getProjects(String url) {
        return Flowable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Document document = Jsoup.connect(url).get();
                Log.w("Fire", "DataManager:54行:" + url);
                Log.e("Fire", "DataManager:57行:" + document.toString());
                return "";
            }
        });
    }
}
