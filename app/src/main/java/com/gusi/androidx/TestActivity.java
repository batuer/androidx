package com.gusi.androidx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * @author Ylw
 * @since 2021/11/28 18:41
 */
public class TestActivity extends Activity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
  }

  public void test(View view) {


  }
}
