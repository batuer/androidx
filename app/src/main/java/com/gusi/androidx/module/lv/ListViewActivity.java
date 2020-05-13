package com.gusi.androidx.module.lv;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    private List<TextView> mHeadList = new ArrayList<>();
    private List<TextView> mFooterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        // Example of a call to a native method
        mTv = findViewById(R.id.sample_text);


        mListView = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItemList);
        mAdapter = adapter;
//        mAdapter = new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return mItemList.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                if (convertView == null) {
//                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
//                }
//                TextView textView = (TextView)convertView;
//                textView.setText(mItemList.get(position));
//                return convertView;
//            }
//        };
        mListView.setAdapter(mAdapter);
//        Toast.makeText()
    }

    public void addItem(View view) {
        Log.w("Fire", "MainActivity:122行:" + get());
        mItemList.add("Item: " + System.currentTimeMillis());
        mAdapter.notifyDataSetChanged();
        Log.e("Fire", "MainActivity:63行:" + get());
    }

    public void removeItem(View view) {
        Log.w("Fire", "MainActivity:67行:" + get());
        if (!mItemList.isEmpty()) {
            mItemList.remove(0);
            mAdapter.notifyDataSetChanged();
        }
        Log.e("Fire", "MainActivity:72行:" + get());
    }

    public void addHead(View view) {
        Log.w("Fire", "MainActivity:76行:" + get());
        TextView textView = (TextView)View.inflate(this, android.R.layout.simple_list_item_1, null);
        textView.setText("Head: " + System.currentTimeMillis());
        mHeadList.add(textView);
        mListView.addHeaderView(textView);
        Log.e("Fire", "MainActivity:81行:" + get());
    }

    public void removeHead(View view) {
        Log.w("Fire", "MainActivity:85行:" + get());
        if (!mHeadList.isEmpty()) {
            TextView textView = mHeadList.remove(0);
            mListView.removeHeaderView(textView);
        }
        Log.e("Fire", "MainActivity:90行:" + get());
    }

    public void addFoot(View view) {
        Log.w("Fire", "MainActivity:94行:" + get());
        TextView textView = (TextView)View.inflate(this, android.R.layout.simple_list_item_1, null);
        textView.setText("Foot: " + System.currentTimeMillis());
        mFooterList.add(textView);
        mListView.addFooterView(textView);
        Log.e("Fire", "MainActivity:99行:" + get());
    }

    public void removeFoot(View view) {
        Log.w("Fire", "MainActivity:103行:" + get());
        if (!mFooterList.isEmpty()) {
            TextView textView = mFooterList.remove(0);
            mListView.removeFooterView(textView);
        }
        Log.e("Fire", "MainActivity:108行:" + get());
    }

    private String get() {
        return "ListViewCount: " + mListView.getCount() + " ,AdapterCount: " + mAdapter.getCount() + " ,HeadCount: "
            + mListView.getHeaderViewsCount() + " ,FootCount: " + mListView.getFooterViewsCount();
    }
}
