package com.gusi.androidx.module.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.gusi.androidx.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    private Cursor mCursor;
    private ListView mListView;

    public BlankFragment() {
    }


    public static BlankFragment newInstance(String from) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString("From", from);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView textView = view.findViewById(R.id.tv);
        textView.setText(getArguments().getString("From") + "_" + this);
        textView.setOnClickListener(v -> query());
        mListView = view.findViewById(R.id.listView);
        return view;
    }

    private void query() {
        getLoaderManager().initLoader(2, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                MyCursorLoader cursorLoader = new MyCursorLoader(getContext(),
                        Uri.parse("content://com.android.contacts/data"),
                        new String[]{"data1", "data4", "_id"}, null, null, null);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                mCursor = cursor;
                mListView.setAdapter(new CursorAdapter(getContext(), cursor) {
                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        int item1 = android.R.layout.simple_list_item_1;
                        View inflate = getLayoutInflater().inflate(item1, parent, false);
                        return inflate;
                    }

                    @Override
                    public void bindView(View view, Context context, Cursor cursor) {
                        TextView textView = (TextView) view;
                        textView.setText(cursor.getString(cursor.getColumnIndex("data1")));
                    }
                });
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                Log.w("Fire", ":onLoaderReset:" + loader + " , " + mCursor.isClosed());
            }
        });
    }


    @Override
    public void onDestroy() {
        if (mCursor == null) {
            super.onDestroy();
        } else {
            Log.w("Fire", "onDestroy:80行:" + isAdded() + " , " + mCursor.isClosed());
            super.onDestroy();
            Log.w("Fire", "onDestroy:81行:" + isAdded() + " , " + mCursor.isClosed());
            if (mCursor != null) {
                new Thread(() -> {
                    while (!mCursor.isClosed()) {
                        Log.w("Fire", "Fragment:onDestroy:-------");
                    }
                }).start();
            }
        }
    }
}