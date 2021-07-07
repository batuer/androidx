package com.gusi.androidx.module.fragment;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

/**
 * @author Ylw
 * @since 2020/7/1 23:20
 */
public class MyCursorAdapter extends CursorAdapter {

    private static final String TAG = "Fire_MyCursorAdapter";

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        Log.w(TAG, "getCount: " + count);
        return count;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view;
        textView.setText(cursor.getString(cursor.getColumnIndex("data1")));
    }

    @Override
    protected void onContentChanged() {
        super.onContentChanged();
        Log.i(TAG, "onContentChanged: ");
        Log.d(TAG, Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.i(TAG, "notifyDataSetChanged: ");
        Log.d(TAG, Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        Log.i(TAG, "notifyDataSetInvalidated: ");
        Log.d(TAG, Log.getStackTraceString(new Throwable()));
    }
}
