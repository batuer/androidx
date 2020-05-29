package com.gusi.androidx.module.lv;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.gusi.androidx.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends Activity {
    private TextView mTv;
    private ListView mListView;
    private BaseAdapter mAdapter;
    private TextView mHeadView;
    private TextView mFootView;

    private List<String> mItemList = new ArrayList<>();
    private List<View> mHeadList = new ArrayList<>();
    private List<View> mFooterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.i("Fire_ListView", "run: " + getTopActivity(ListViewActivity.this) + " ," + hasWindowFocus());
                    SystemClock.sleep(100);
                }
            }
        }).start();


        View decorView = getWindow().getDecorView();
        WindowManager windowManager = getWindowManager();

        decorView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Fire", "ListViewActivity:40行:" + hasFocus);
                Log.i("Fire", Log.getStackTraceString(new Throwable()));
            }
        });
//        decorView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(ListViewActivity.this, ViewActivity.class));
//            }
//        }, 500);


        // Example of a call to a native method
        mTv = findViewById(R.id.sample_text);


        mListView = findViewById(R.id.list);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItemList);
//        mAdapter = adapter;
        mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
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
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item, parent, false);
                }
                TextView textView = convertView.findViewById(R.id.tv);
                textView.setText(mItemList.get(position));
                Log.w("Fire",
                        "ListViewActivity:61行:" + ViewCompat.getLayoutDirection(convertView) + " : " + ViewCompat.getLayoutDirection(parent));
                return convertView;
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.requestLayout();
//        Toast.makeText()
    }

    public void addItem(View view) {
        mItemList.add("Item: ");
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(View view) {

        if (!mItemList.isEmpty()) {
//            mItemList.get(0).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            mAdapter.notifyDataSetChanged();
        }

    }

    public void addHead(View view) {
        View inflate = View.inflate(this, R.layout.item, null);
        TextView textView = inflate.findViewById(R.id.tv);
        textView.setText("Head: ");
        mHeadList.add(inflate);
        mListView.addHeaderView(inflate);
        Log.w("Fire", "93行:" + ViewCompat.getLayoutDirection(inflate));

    }

    public void removeHead(View view) {
        mHeadList.get(0).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//        Log.w("Fire", "MainActivity:85行:" + get());
//        if (!mHeadList.isEmpty()) {
//            TextView textView = mHeadList.remove(0);
//            mListView.removeHeaderView(textView);
//        }
//        Log.e("Fire", "MainActivity:90行:" + get());
    }

    public void addFoot(View view) {
        View inflate = View.inflate(this, R.layout.item, null);
        TextView textView = inflate.findViewById(R.id.tv);
        textView.setText("Foot: ");
        mFooterList.add(inflate);
        mListView.addFooterView(inflate);
        Log.w("Fire", "112:" + ViewCompat.getLayoutDirection(inflate));
    }

    public void removeFoot(View view) {
//        LayoutInflater.from(getApplicationContext());
//        mFooterList.get(0).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//        Log.w("Fire", "MainActivity:103行:" + get());
//        if (!mFooterList.isEmpty()) {
//            TextView textView = mFooterList.remove(0);
//            mListView.removeFooterView(textView);
//        }
//        Log.e("Fire", "MainActivity:108行:" + get());
        LinearLayout linearLayout = findViewById(R.id.ll);
        test(linearLayout);

    }

    private void test(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View child = linearLayout.getChildAt(i);
            if (child instanceof LinearLayout) {
                test((LinearLayout) child);
            } else {
                boolean b = child.hasWindowFocus();
            }
        }
    }

    public void dialog(View view) {
//        new AlertDialog.Builder(Utils.getApp()).setTitle("Title").setMessage("Message").show();
        Dialog dialog = new MyDialog(this);
        dialog.setTitle("Title");
        View inflate = View.inflate(this, R.layout.item, null);
        TextView textView = inflate.findViewById(R.id.tv);
        textView.setText("Message... ");
        dialog.setContentView(inflate);
        dialog.show();

    }

//    private String get() {
//        return "ListViewCount: " + mListView.getCount() + " ,AdapterCount: " + mAdapter.getCount() + " ,HeadCount: "
//                + mListView.getHeaderViewsCount() + " ,FootCount: " + mListView.getFooterViewsCount();
//    }


    @Override
    protected void onPause() {
        Log.w("Fire_ListView", "ListViewActivity:onPause:" + hasWindowFocus() + " ," + getTopActivity(this));
        super.onPause();
        Log.w("Fire_ListView", "ListViewActivity:onPause:" + hasWindowFocus() + " ," + getTopActivity(this));
    }

    @Override
    protected void onResume() {
        Log.w("Fire_ListView", "ListViewActivity:onResume:" + hasWindowFocus() + " ," + getTopActivity(this));
        super.onResume();
        Log.w("Fire_ListView", "ListViewActivity:onResume:" + hasWindowFocus() + " ," + getTopActivity(this));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i("Fire11", Log.getStackTraceString(new Throwable()));
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("Fire_ListView",
                "dispatchTouchEvent:185行:   " + ev.getAction() + ",hasWindowFocus = " + hasWindowFocus() + " ,Top = " + getTopActivity(this));
        return super.dispatchTouchEvent(ev);
    }

    public String getTopActivity(Context context) {
        android.app.ActivityManager manager =
                (android.app.ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity).toString();
        } else
            return null;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e("Fire", "ListViewActivity:239行:" + hasFocus);
    }

    public void popupWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(this);
        View inflate = View.inflate(this, R.layout.item, null);
        TextView textView = inflate.findViewById(R.id.tv);
        textView.setText("popupWindow... \n popupWindow");
        popupWindow.setContentView(inflate);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.showAsDropDown(view);
        Log.w("Fire", "ListViewActivity:isFocusable:" + popupWindow.isFocusable());
        popupWindow.setFocusable(true);
    }
}
