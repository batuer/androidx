package com.gusi.androidx.module.lv;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

/**
 * @author Ylw
 * @since 2020/7/21 22:19
 */
public class MyLv extends ListView {
  private static final String TAG = "Fire_MyLv";

  private byte[] bys = new byte[1024 * 1024 * 30];

  public MyLv(Context context) {
    super(context);
  }

  public MyLv(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MyLv(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    Log.w(TAG, "onDetachedFromWindow: ");
  }

  @Override
  public void requestLayout() {
    super.requestLayout();
//        Log.i(TAG, "requestLayout: ");
  }
}
