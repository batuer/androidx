package com.gusi.androidx.module.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gusi.androidx.R;
import com.gusi.androidx.module.view.NameBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DbFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DbFragment extends Fragment {

    private static final String TAG = "Fire_DbFragment";
    private ContentObserver mContentObserver;

    public static DbFragment newInstance() {
        DbFragment fragment = new DbFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentObserver = new MyContentObserver(null);
        getContext().getContentResolver().registerContentObserver(Uri.parse("content://com.android.contacts/data"),
                false, mContentObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db, container, false);
        view.findViewById(R.id.tv_insert).setOnClickListener(v -> {
            addContact(NameBuilder.build(), NameBuilder.getMobilePhone());
        });
        view.findViewById(R.id.tv_test).setOnClickListener(v -> test());
        return view;
    }

    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getView().requestLayout();
                Log.w(TAG, "run: ");
            }
        }).start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        addContact(NameBuilder.build(), NameBuilder.getMobilePhone());
        getContext().getContentResolver().unregisterContentObserver(mContentObserver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    private class MyContentObserver extends ContentObserver {
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, (ViewGroup) getView(),
                    false);
            ((ViewGroup) getView()).addView(view);
            Log.e(TAG, (Looper.myLooper() == Looper.getMainLooper()) + " :onChange: " + Thread.currentThread());
            if (true){
                throw new NullPointerException(
                        "Only the original thread that created a view hierarchy can touch its views.");
            }
        }

    }
}