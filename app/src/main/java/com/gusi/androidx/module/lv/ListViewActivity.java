package com.gusi.androidx.module.lv;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.gusi.androidx.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListViewActivity extends Activity {
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
        // Example of a call to a native method

        mListView = findViewById(R.id.list);
        mAdapter = getAdapter(false, 5);
        mListView.setAdapter(mAdapter);
    }

    public void addItem(View view) {
        mItemList.add("Item: ");
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(View view) {
        if (!mItemList.isEmpty()) {
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

    public String getTopActivity(Context context) {
        android.app.ActivityManager manager =
                (android.app.ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity).toString();
        } else
            return null;
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

    public void dynamic(View view) {
        mListView.setAdapter(getAdapter(true, 10));
    }

    public void static1(View view) {
        mListView.setAdapter(getAdapter(false, 20));
    }


    boolean mIsDynamic = false;

    public void notify(View view) {
        mIsDynamic = true;
        BaseAdapter adapter = (BaseAdapter) mListView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    public void requestLayout(View view) {
        mIsDynamic = false;
        mListView.requestLayout();
    }

    private BaseAdapter getAdapter(boolean isDynamic, int count) {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                int realCount = mIsDynamic ? new Random().nextInt(10) + 2 : count;
//                int count = isDynamic ? mCount : 20;
                int itemCount = mListView.getCount();
                Log.w("Fire", "ListViewActivity:162行:" + itemCount + " : " + count);
                Log.i("Fire_" + itemCount + " , " + count, Log.getStackTraceString(new Throwable()));
                if (itemCount != 0 && itemCount != count) {
                    Log.e("Fire", "ListViewActivity:152行:" + itemCount + " : " + count);
                    notifyDataSetInvalidated();
                    return 0;
                }
                return realCount;
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
                textView.setText("Item : " + position);
                return convertView;
            }
        };
    }
}
