package com.gusi.androidx.module.net;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.blankj.utilcode.util.CloseUtils;
import com.gusi.androidx.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetActivity extends Activity {

    private static final String TAG = "Ylw_NetAct";
    private ExecutorService mService;
    private Login mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        mService = Executors.newCachedThreadPool();
    }


    public void urlConnectionGet(View view) {
        mService.execute(() -> performUrlConnectionGet());
    }

    private void performUrlConnectionGet() {
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://www.wanandroid.com//hotkey/json");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(8000);
            httpURLConnection.setUseCaches(true);
            InputStream in = httpURLConnection.getInputStream();
            //对获得的流进行读取
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            //处理相应
            handleResponse(response.toString());
        } catch (Exception e) {
            Log.e(TAG, "performUrlConnectionGet: ", e);
        } finally {
            CloseUtils.closeIOQuietly(reader);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    private void handleResponse(String json) {
        BaseResponse<List<HotKey>> request = JsonUtils.jsonToHotKey(json);
        Log.i(TAG, "handleResponse: " + request);
    }

    public void urlConnectionPost(View view) {
//        String url = "https://www.wanandroid.com/user/login";
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("username", "18609573806");
//        paramsMap.put("password", "batuer110");
//        UrlHttpUtil.post(url, paramsMap, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(int code, String errorMessage) {
//                Log.i(TAG, "onFailure: " + code + ":" + errorMessage);
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.i(TAG, "onResponse: " + response);
//            }
//        });
//        if (mLogin == null) {
//            Toast.makeText(this, "请登录!", Toast.LENGTH_SHORT).show();
//            return;
//        }
        mService.execute(() -> {
            String url = "https://www.wanandroid.com/lg/uncollect_originId/2333/json";

            try {
                HashMap<String, String> headerMap = new HashMap<>();
                headerMap.put("Set-Cookie","JSESSIONID=7CE8AD4DEAA028FB2D00B52338375E2E");
                headerMap.put("Cookie", "JSESSIONID=7CE8AD4DEAA028FB2D00B52338375E2E");
                String data = new UrlConnectionRequest().postData(url, null, null, null);
                Log.i(TAG, "login: " + data);
                if (!TextUtils.isEmpty(data)) {

                }
            } catch (IOException e) {
                Log.e(TAG, "login: ", e);
            }

        });

    }

    public void login(View view) {
        mService.execute(() -> {
            String url = "https://www.wanandroid.com/user/login";
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("username", "18609573806");
            paramsMap.put("password", "batuer110");
            try {
                Pair<String, String> data = new UrlConnectionRequest().postLogin(url, paramsMap, null, null);
                Log.i(TAG, "login: " + data.first + ":" + data.second);
                if (!TextUtils.isEmpty(data.first)) {
                    BaseResponse<Login> loginResponse = JsonUtils.jsonToLogin(data.first);
                    mLogin = loginResponse.getData();
                    mLogin.setToken(data.second);
                    Log.i(TAG, "login: " + loginResponse);
                }
            } catch (IOException e) {
                Log.e(TAG, "login: ", e);
            }
        });
    }
}