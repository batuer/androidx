package com.gusi.androidx.module.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.gusi.androidx.R;
import com.gusi.androidx.module.db.MyBaseCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class BlankFragment extends Fragment {
    private static final String TAG = "Fire_BlankFragment";
    private Cursor mCursor;
    private ListView mListView;
    private MyBaseCursorAdapter mCursorAdapter;
    private LoaderManager.LoaderCallbacks<Cursor> mRestartCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
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
            mCursorAdapter.changeCursor(cursor);
            MyCursorLoader myCursorLoader = (MyCursorLoader) loader;
            Cursor loaderCursor = myCursorLoader.getCursor();
            Log.w("Fire_" + cursor, "BlankFragment:54行:" + loader);
            Log.i(TAG, Log.getStackTraceString(new Throwable()));
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            Log.w("Fire_" + loader, Log.getStackTraceString(new Throwable()));
        }
    };
    private LoaderManager.LoaderCallbacks<Cursor> mInitCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
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
            MyCursorLoader myCursorLoader = (MyCursorLoader) loader;
            Cursor loaderCursor = myCursorLoader.getCursor();
            Log.w("Fire_" + cursor, "BlankFragment:77行:" + loaderCursor + " : " + (loaderCursor == null ? "" :
                    loaderCursor.isClosed()));
            mCursor = cursor;
            mCursorAdapter.changeCursor(cursor);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            Log.w("Fire", ":onLoaderReset:" + loader + " , " + mCursor.isClosed());
        }
    };

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
        TextView textView = view.findViewById(R.id.tv_init);
        view.findViewById(R.id.tv_restart).setOnClickListener(v -> restart());
        textView.setText(getArguments().getString("From") + "_" + this.hashCode());
        textView.setOnClickListener(v -> init());
        mListView = view.findViewById(R.id.listView);
        mCursorAdapter = new MyBaseCursorAdapter(getContext(), null) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public int getCount() {
                return super.getCount();
            }

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
        };
        mListView.setAdapter(mCursorAdapter);
        return view;
    }

    private void restart() {
        getLoaderManager().restartLoader(1, null, mRestartCallback);
    }

    private void init() {
        getLoaderManager().initLoader(2, null, mInitCallback);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
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