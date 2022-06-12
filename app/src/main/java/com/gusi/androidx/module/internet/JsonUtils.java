package com.gusi.androidx.module.internet;

import com.google.gson.JsonObject;

/**
 * @author Ylw
 * @since 2022/6/12 9:35
 */
public class JsonUtils {
  public static <T> BaseRequest<T> jsonToObj(String json) {
    BaseRequest<T> request = new BaseRequest<>();
    if (json == null) {
      return request;
    }
    JsonObject jsonObject = new JsonObject();


    return request;
  }
  public static void test(String json){


  }
}
