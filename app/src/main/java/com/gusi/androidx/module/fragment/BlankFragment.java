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
import com.gusi.androidx.base.BaseFragment;
import com.gusi.androidx.module.db.CursorAdapter;
import com.gusi.androidx.module.view.NameBuilder;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends BaseFragment {
    private static final String TAG = "Fire_BlankFragment";

    private ListView mListView;

    private boolean isFirst;

    private MyAdapter mBaseAdapter;
//    private MyCursorAdapter mBaseAdapter;

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
            mBaseAdapter.setCursor(cursor);
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
            mBaseAdapter.setCursor(cursor);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        }
    };
    private Uri mUri;


    public static BlankFragment newInstance(String from) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString("From", from);
        fragment.setArguments(args);
        MyCursorLoader.DEBUG = true;
        return fragment;
    }



    @Override
    protected int getLayout() {
        return R.layout.fragment_blank;
    }

    @Override
    protected void initView() {
        super.initView();
        View view = getView();
        TextView textView = view.findViewById(R.id.tv_init);
        view.findViewById(R.id.tv_restart).setOnClickListener(v -> restart());
        textView.setOnClickListener(v -> init());
        view.findViewById(R.id.tv_insert).setOnClickListener(v -> {
            addContact(NameBuilder.build(), NameBuilder.getMobilePhone());
        });
        view.findViewById(R.id.tv_update).setOnClickListener(v -> {
            update(NameBuilder.build(), NameBuilder.getMobilePhone());
            mBaseAdapter.notifyDataSetInvalidated();
        });
        view.findViewById(R.id.tv_invalidated).setOnClickListener(v -> {

        });
        mListView = view.findViewById(R.id.listView);
        new CursorAdapter(getContext(), null) {
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

        mBaseAdapter = new MyAdapter(getLayoutInflater());
//        mBaseAdapter = new MyCursorAdapter(getActivity(), null);
        mListView.setAdapter(mBaseAdapter);
    }

    @Override
    protected void initInject() {

    }

    private void restart() {
        getLoaderManager().restartLoader(1, null, mRestartCallback);
    }

    private void init() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(2, null, mInitCallback);
    }

    public void addContact(String name, String number) {
        // 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        ContentResolver resolver = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        Uri rawContactUri = resolver.insert(
                ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        // 往data表插入姓名数据
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName
                .CONTENT_ITEM_TYPE);// 内容类型
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        resolver.insert(ContactsContract.Data.CONTENT_URI,
                values);

        // 往data表插入电话数据
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        mUri = resolver.insert(ContactsContract.Data.CONTENT_URI,
                values);
    }

    public void update(String name, String number) {
//        if (mUri == null) {
//            return;
//        }
//        ContentResolver resolver = getActivity().getContentResolver();
//        ContentValues values = new ContentValues();
//        long rawContactId = ContentUris.parseId(mUri);
//        // 往data表插入电话数据
//        values.clear();
//        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
//        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
//        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, "number" + number);
//        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
//        resolver.update(ContactsContract.Data.CONTENT_URI, values, null, null);

    }

}