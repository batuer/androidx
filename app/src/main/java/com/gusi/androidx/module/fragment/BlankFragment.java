package com.gusi.androidx.module.fragment;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.gusi.androidx.module.view.NameBuilder;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class BlankFragment extends Fragment {
    private static final String TAG = "Fire_BlankFragment";

    private ListView mListView;

    private boolean isFirst;

    //    private MyAdapter mBaseAdapter;
    private MyCursorAdapter mBaseAdapter;

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
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
            Log.i(TAG, "restartOnLoadFinished: loader = " + loader + " ,cursor = " + cursor);
            if (isFirst) {
                Log.e("Fire",
                        "restartFinished: = " + loader + " ,cursor = " + cursor + " ," + mListView.isInLayout() + " ,"
                                + mListView.isLayoutRequested());
                mBaseAdapter.notifyDataSetChanged();
                Log.e("Fire",
                        "restartFinished: = " + loader + " ,cursor = " + cursor + " ," + mListView.isInLayout() + " ,"
                                + mListView.isLayoutRequested());
                return;
            }
            mBaseAdapter.changeCursor(cursor);
            isFirst = true;
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
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
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
            Log.i(TAG, "initOnLoadFinished: loader = " + loader + " ,cursor = " + cursor);
            if (isFirst) {
                Log.e("Fire", "initFinished: = " + loader + " ,cursor = " + cursor + " ," + mListView.isInLayout() +
                        " ," + mListView.isLayoutRequested());
//                mBaseAdapter.notifyDataSetChanged();
//                Log.e("Fire", "initFinished: = " + loader + " ,cursor = " + cursor + " ," + mListView.isInLayout() +
//                        " ," + mListView.isLayoutRequested());
                return;
            }
            mBaseAdapter.changeCursor(cursor);
            isFirst = true;
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        }
    };


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
        textView.setOnClickListener(v -> init());
        view.findViewById(R.id.tv_insert).setOnClickListener(v -> {
            addContact(NameBuilder.build(), NameBuilder.getMobilePhone());
        });
        mListView = view.findViewById(R.id.listView);
        new MyBaseCursorAdapter(getContext(), null) {
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

//        mBaseAdapter = new MyAdapter(getLayoutInflater());
        mBaseAdapter = new MyCursorAdapter(getActivity(), null);
        mListView.setAdapter(mBaseAdapter);
        return view;
    }

    public void addContact(String name, String number) {

        ContentValues values = new ContentValues();
        // 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        ContentResolver resolver = getActivity().getContentResolver();
        Uri rawContactUri = resolver.insert(
                ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        // 往data表插入姓名数据
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);// 内容类型
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        resolver.insert(ContactsContract.Data.CONTENT_URI,
                values);

        // 往data表插入电话数据
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        resolver.insert(ContactsContract.Data.CONTENT_URI,
                values);
    }

    private void restart() {
        getLoaderManager().restartLoader(1, null, mRestartCallback);
    }

    private void init() {
        getLoaderManager().initLoader(2, null, mInitCallback);
    }
}