package com.gusi.androidx.module.fragment;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

/**
 * @author Ylw
 * @since 2020/6/29 23:06
 */
public class MyAdapter extends BaseAdapter {
    private static final String TAG = "Fire_MyAdapter";
    private LayoutInflater mInflater;
    private Cursor mCursor;
    private int mI;

    private MyDataSetObserver mDataSetObserver = new MyDataSetObserver();

    public Cursor getCursor() {
        return mCursor;
    }

    public void setCursor(Cursor cursor) {

        mCursor = cursor;
        notifyDataSetChanged();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mCursor.close();
                Log.e(TAG, "run: close");
            }
        }, 3000);
//        mCursor.registerDataSetObserver(mDataSetObserver);
    }

    public MyAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    @Override
    public int getCount() {
        mI = mCursor != null && !mCursor.isClosed() ? mCursor.getCount() : 0;
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
        try {
            convertView = getItemView(position, convertView, parent);
        } catch (Exception e) {
//            notifyDataSetInvalidated();
            notifyDataSetChanged();
            Log.e(TAG, "getView: " + e.toString() );
        }
        return convertView;
    }

    @NotNull
    private View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        mCursor.moveToPosition(position);
        TextView textView = (TextView) convertView;
        textView.setText(mCursor.getString(mCursor.getColumnIndex("data1")) + "  count = " + mI);
        return convertView;
    }

    private class MyDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
            Log.w(TAG, "onChanged: ");
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            notifyDataSetChanged();
            Log.w(TAG, "onInvalidated: ");
        }
    }
}
