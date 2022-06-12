package com.gusi.androidx.module.net;

import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.blankj.utilcode.util.CloseUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Ylw
 * @since 2022/6/12 20:56
 */
public class UrlConnectionRequest {
    private static final String TAG = "Ylw_UrlConnectionRequest";


    public Pair<String, String> postLogin(String url, Map<String, String> params, String bodyType,
                                          Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = getHttpURLConnection(url, "POST");
        conn.setDoOutput(true);// 可写出
        conn.setDoInput(true);// 可读入
        conn.setUseCaches(false);// 不是有缓存
        if (!TextUtils.isEmpty(bodyType)) {
            conn.setRequestProperty("Content-Type", bodyType);
        }
        if (headerMap != null) { // 请求头必须放在conn.connect()之前
            for (String key : headerMap.keySet()) {
                conn.setRequestProperty(key, headerMap.get(key));
            }
        }

        conn.connect();// 连接，以上所有的请求配置必须在这个API调用之前

        String body = getPostBodyFormParamsMap(params);
        if (!TextUtils.isEmpty(body)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(body);
            CloseUtils.closeIOQuietly(writer);
        }

        String headerField = conn.getHeaderField("Set-Cookie");
        // token_pass_wanandroid_com=aaae7bf60cf776fb568c51b7b1144827; Domain=wanandroid.com; Expires=Tue,
        // 12-Jul-2022 13:50:48 GMT; Path=/
        Log.i(TAG, "postData: " + headerField);
        //
        String response = getRetString(conn.getInputStream());
        conn.disconnect();
        return new Pair<>(response, headerField);
    }


    public String postData(String url, Map<String, String> params, String bodyType,
                           Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = getHttpURLConnection(url, "POST");
        conn.setDoOutput(true);// 可写出
        conn.setDoInput(true);// 可读入
        conn.setUseCaches(false);// 不是有缓存
        if (!TextUtils.isEmpty(bodyType)) {
            conn.setRequestProperty("Content-Type", bodyType);
        }
        if (headerMap != null) { // 请求头必须放在conn.connect()之前
            for (String key : headerMap.keySet()) {
                conn.setRequestProperty(key, headerMap.get(key));
            }
        }
        conn.connect();// 连接，以上所有的请求配置必须在这个API调用之前

        String body = getPostBodyFormParamsMap(params);
        if (!TextUtils.isEmpty(body)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(body);
            CloseUtils.closeIOQuietly(writer);
        }

        String headerField = conn.getHeaderField("Set-Cookie");
        // token_pass_wanandroid_com=aaae7bf60cf776fb568c51b7b1144827; Domain=wanandroid.com; Expires=Tue,
        // 12-Jul-2022 13:50:48 GMT; Path=/
        Log.i(TAG, "postData: " + headerField);
        //
        String response = getRetString(conn.getInputStream());
        conn.disconnect();
        return response;
    }

    /**
     * 得到bodyType
     */
    private String getPostBodyType(Map<String, String> paramsMap, String jsonStr) {
        if (paramsMap != null) {
            //return "text/plain";不知为什么这儿总是报错。目前暂不设置(20170424)
            return null;
        } else if (!TextUtils.isEmpty(jsonStr)) {
            return "application/json;charset=utf-8";
        }
        return null;
    }

    /**
     * 根据键值对参数得到body
     */
    private String getPostBodyFormParamsMap(Map<String, String> params) throws IOException {//throws
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();

        boolean isFirst = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }


    private String getRetString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        CloseUtils.closeIOQuietly(is, reader);
        return sb.toString();
    }

    /**
     * 得到Connection对象，并进行一些设置
     */
    private HttpURLConnection getHttpURLConnection(String requestURL, String requestMethod) throws IOException {
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10 * 1000);
        conn.setReadTimeout(15 * 1000);
        conn.setRequestMethod(requestMethod);
        return conn;
    }
}
