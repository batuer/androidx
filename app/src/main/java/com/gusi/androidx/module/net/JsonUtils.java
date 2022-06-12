package com.gusi.androidx.module.net;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ylw
 * @since 2022/6/12 9:35
 */
public class JsonUtils {
    private static final String TAG = "Ylw_JsonUtils";

    public static <T> BaseResponse<T> jsonToObj(String json, boolean isDataArray, Class tClass) {
        BaseResponse<T> request = new BaseResponse<>();
        if (json == null) {
            return request;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            int errorCode = jsonObject.getInt("errorCode");
            String errorMsg = jsonObject.getString("errorMsg");
            if (errorCode != BaseResponse.CODE_SUCCESS) {
                Log.w(TAG, "jsonToObj errorMsg: " + errorMsg);
                return request;
            }
            if (isDataArray) {

            } else {

            }


            Field[] declaredFields = tClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Log.i(TAG, "jsonToObj: " + declaredField);
            }


        } catch (JSONException e) {
            Log.e(TAG, "jsonToObj: ", e);
        }


        return request;
    }

    public static BaseResponse<List<HotKey>> jsonToHotKey(String json) {
        BaseResponse<List<HotKey>> request = new BaseResponse<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int errorCode = jsonObject.getInt("errorCode");
            String errorMsg = jsonObject.getString("errorMsg");
            request.setErrorCode(errorCode);
            request.setErrorMsg(errorMsg);
            if (errorCode != BaseResponse.CODE_SUCCESS) {
                Log.w(TAG, "jsonToObj errorMsg: " + errorMsg);
                return request;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            int length = jsonArray.length();
            List<HotKey> list = new ArrayList<>(length);
            request.setData(list);
            for (int i = 0; i < length; i++) {
                JSONObject hotKeyJson = jsonArray.getJSONObject(i);
                HotKey hotKey = new HotKey();
                hotKey.setName(hotKeyJson.getString("name"));
                hotKey.setId(hotKeyJson.getInt("id"));
                hotKey.setLink(hotKeyJson.getString("link"));
                hotKey.setOrder(hotKeyJson.getInt("order"));
                hotKey.setVisible(hotKeyJson.getInt("visible"));
                list.add(hotKey);
            }
        } catch (JSONException e) {
            Log.e(TAG, "jsonToHotKey: ", e);
        }
        return request;
    }

    public static BaseResponse<Login> jsonToLogin(String json) {
        BaseResponse<Login> response = new BaseResponse<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int errorCode = jsonObject.getInt("errorCode");
            String errorMsg = jsonObject.getString("errorMsg");
            response.setErrorCode(errorCode);
            response.setErrorMsg(errorMsg);
            if (errorCode != BaseResponse.CODE_SUCCESS) {
                Log.w(TAG, "jsonToObj errorMsg: " + errorMsg);
                return response;
            }
            JSONObject dataObj = jsonObject.getJSONObject("data");
            Login login = new Login();
            login.setAdmin(dataObj.getBoolean("admin"));
            login.setCoinCount(dataObj.getInt("coinCount"));
            login.setIcon(dataObj.getString("icon"));
            login.setEmail(dataObj.getString("email"));
            login.setId(dataObj.getInt("id"));
            login.setNickname(dataObj.getString("nickname"));
            login.setPassword(dataObj.getString("password"));
            login.setPublicName(dataObj.getString("publicName"));
            login.setToken(dataObj.getString("token"));
            login.setType(dataObj.getInt("type"));
            login.setUsername(dataObj.getString("username"));
            response.setData(login);
        } catch (JSONException e) {
            Log.e(TAG, "jsonToHotKey: ", e);
        }
        return response;
    }
}
