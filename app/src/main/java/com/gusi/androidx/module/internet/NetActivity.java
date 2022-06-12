package com.gusi.androidx.module.internet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.blankj.utilcode.util.CloseUtils;
import com.gusi.androidx.R;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetActivity extends Activity {

  private static final String TAG = "Ylw_NetAct";
  private ExecutorService mService;

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

  }

  public void urlConnectionPost(View view) {
  }
}