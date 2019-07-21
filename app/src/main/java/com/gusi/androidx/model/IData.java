package com.gusi.androidx.model;

import androidx.core.util.Pair;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @Author ylw 2018/7/25 22:50
 */
public interface IData {
    Flowable<List<Pair<String, String>>> getRegistration(String url);

    Flowable<Object> getProjects(String url);
}
