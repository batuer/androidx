package com.gusi.androidx.module.lv;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.blankj.utilcode.util.TimeUtils;
import com.gusi.androidx.R;
import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends Activity implements AdapterView.OnItemClickListener {
  private static final String TAG = "Fire_ListView";
  private ListView mListView;
  private BaseAdapter mAdapter;
  private List<String> mItemList = new ArrayList<>();

  private static byte[] bys = new byte[1024 * 1024 * 60];
  private MyThreadPoolExecutor mExecutor = new MyThreadPoolExecutor();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_view);
    // Example of a call to a native method

    mListView = findViewById(R.id.list);
    mAdapter = getAdapter(false, 5);
    mListView.setAdapter(mAdapter);
    mListView.setOnItemClickListener(this);
    Button btn = findViewById(R.id.btn);
    Runtime runtime = Runtime.getRuntime();
    btn.setText("maxMemory = " + runtime.maxMemory() + "\ntotalMemory = " + runtime.totalMemory() + "\nfreeMemory = " + runtime.freeMemory());
  }


  public void addItem(View view) {
    mExecutor.execute(new MyThreadPoolExecutor.MyRunnable(TimeUtils.getNowString()));
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.w(TAG, Log.getStackTraceString(new Throwable("onPause")));
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.w(TAG, Log.getStackTraceString(new Throwable("onStop")));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.w(TAG, Log.getStackTraceString(new Throwable("onDestroy")));
  }

  @Override
  public void finish() {
    Log.w(TAG, "finish: ");
    super.finish();
    Log.e(TAG, "finish: ");
  }

  public void removeItem(View view) {
    if (!mItemList.isEmpty()) {
      mAdapter.notifyDataSetChanged();
    }
  }

  public void dialog(View view) {
//        new AlertDialog.Builder(Utils.getApp()).setTitle("Title").setMessage("Message").show();
//        Dialog dialog = new MyDialog(this);
//        dialog.setTitle("Title");
//        View inflate = View.inflate(this, R.layout.item, null);
//        TextView textView = inflate.findViewById(R.id.tv_init);
//        textView.setText("Message... ");
//        dialog.setContentView(inflate);
//        dialog.show();
    Runtime runtime = Runtime.getRuntime();
    Log.w(TAG, "dialog: " + runtime.maxMemory() + " : " + runtime.totalMemory() + " : " + runtime.freeMemory());
  }


  public void popupWindow(View view) {
    View inflate = View.inflate(this, R.layout.item, null);
    PopupWindow popupWindow = new PopupWindow(getApplicationContext());
    TextView textView = inflate.findViewById(R.id.tv_init);
    textView.setText("popupWindow... \n popupWindow");
    popupWindow.setContentView(inflate);
    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    popupWindow.showAsDropDown(view);
    Log.w("Fire", "ListViewActivity:isFocusable:" + popupWindow.isFocusable());
    popupWindow.setFocusable(true);
  }


  private BaseAdapter getAdapter(boolean isDynamic, int count) {
    return new BaseAdapter() {
      @Override
      public int getCount() {
        Log.i(TAG, "getCount: ");
        return mItemList.size();
      }

      @Override
      public Object getItem(int position) {
        return null;
      }

      @Override
      public long getItemId(int position) {
        return position;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, Log.getStackTraceString(new Throwable()));
        if (convertView == null) {
          convertView = getLayoutInflater().inflate(R.layout.item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.tv_init);
        textView.setText(mItemList.get(position));
        return convertView;
      }
    };
  }

  /**
   * 删除Item动画
   *
   * @param view     所要删除的convertView
   * @param position
   */
  int count = 0;

  private void deleteItem(View view, final int position) {
    count++;
    if (count == 8) {
      finish();
      Log.e(TAG, "finish(): ");
    }
    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        mItemList.remove(position);
        mAdapter.notifyDataSetChanged();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    };
    collapse(view, animationListener);
  }

  /**
   * Item缩放的动画
   *
   * @param view
   * @param animationListener 这个Item动画不仅仅可以缩小高度，也可以左右滑动删除等
   */
  public void collapse(final View view, Animation.AnimationListener animationListener) {
    final int originHeight = view.getMeasuredHeight();
    Animation animation = new Animation() {
      @Override
      protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (interpolatedTime == 1.0f) {
          view.getLayoutParams().height = originHeight;//更改部分避免删除两个Item
        } else {
          view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
        }
        view.requestLayout();
      }

      @Override
      public boolean willChangeBounds() {
        return true;
      }
    };
    animation.setAnimationListener(animationListener);
    animation.setDuration(300);
    view.startAnimation(animation);
  }

  public void io(View view) {

  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    deleteItem(view, position);
  }
}
