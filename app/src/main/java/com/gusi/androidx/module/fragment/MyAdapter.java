package com.gusi.androidx.module.fragment;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Ylw
 * @since 2020/6/29 23:06
 */
public class MyAdapter extends BaseAdapter {
    private static final String TAG = "Fire_MyAdapter";
    private LayoutInflater mInflater;
    private Cursor mCursor;
    private int mI;

    public Cursor getCursor() {
        return mCursor;
    }

    public void setCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public MyAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    @Override
    public int getCount() {
        int cursorCount = -3;
        if (mCursor != null) {
            cursorCount = mCursor.isClosed() ? -2 : mCursor.getCount();
        }
        if (cursorCount == -2) {
            Log.i(TAG, Log.getStackTraceString(new Throwable()));
        }
        mI = mCursor != null && !mCursor.isClosed() ? mCursor.getCount() : 0;
        Log.d("Fire", "MyAdapter:39行:" + mI + " ,cursorCount = " + cursorCount);
        return mI;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        mCursor.moveToPosition(position);
        TextView textView = (TextView) convertView;
        textView.setText(mCursor.getString(mCursor.getColumnIndex("data1")) + "  count = " + mI);
        return convertView;
    }
}
