package com.gusi.androidx.module.view;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.CursorAdapter;

import com.gusi.androidx.R;

import java.lang.ref.WeakReference;

/**
 * @author Ylw
 * @since 2019/8/24 23:17
 */
public class ViewActivity extends Activity {
    private static final String TAG = "Fire";
    private String[] permissionList = new String[]{ // 申请的权限列表
            Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.READ_CONTACTS};
    private Uri callUri = CallLog.Calls.CONTENT_URI;
    private String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
            , CallLog.Calls.NUMBER// 通话记录的电话号码
            , CallLog.Calls.DATE// 通话记录的日期
            , CallLog.Calls.DURATION// 通话时长
            , CallLog.Calls.TYPE};// 通话类型}
    String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
    private HandlerThread mHandlerThread;
    private Cursor mCursor;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        mListView = findViewById(R.id.listView);
    }

    public void clickTest(View view) {
        view.requestLayout();
    }


    public void query(View view) {
        getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                MyCursorLoader cursorLoader = new MyCursorLoader(ViewActivity.this,
                        Uri.parse("content://com.android.contacts/data"),
                        new String[]{"data1", "data4", "_id"}, null, null, null);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                mCursor = cursor;
                mListView.setAdapter(new CursorAdapter(ViewActivity.this, cursor) {
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
            public void onLoaderReset(Loader<Cursor> loader) {
                Log.w("Fire", "ViewActivity:onLoaderReset:" + loader + " , " + mCursor.isClosed());
            }
        });
    }


    // 获取联系人
    private void getConnect() {
        String[] projection = {};
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{"display_name", "sort_key", "contact_id", "data1"}, null, null, null);
        Log.i(TAG, "cursor connect count:" + cursor.getCount());
        Log.w("Fire", "ViewActivity:59行:" + contentResolver);
        // moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            // 读取通讯录的姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            // 读取通讯录的号码
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Log.i(TAG, "获取的通讯录是： " + name + "\n" + " number : " + number);
        }
        cursor.close();
    }


    public void handleThread(View view) {
        addContact(NameBuilder.build(), NameBuilder.getMobilePhone());
    }

    public void insertContact(View view) {
//        if (mHandlerThread != null) {
//            mHandlerThread.quit();
//        }
        addContact("YuLiwen", "123546852565");
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String build = NameBuilder.build();
                String mobilePhone = NameBuilder.getMobilePhone();
                addContact(build, mobilePhone);
                Log.i(TAG, build + " : " + mobilePhone);
            }

        }).start();
    }

    public void addContact(String name, String number) {

        ContentValues values = new ContentValues();
        // 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        Uri rawContactUri = getContentResolver().insert(
                ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        // 往data表插入姓名数据
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);// 内容类型
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                values);

        // 往data表插入电话数据
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                values);
    }

    public void asyncQueryHandler(View view) {
        MyAsyncQueryHandler asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver()) {

        };

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    WeakReference<ContentResolver> resolver = asyncQueryHandler.getResolver();
                    WeakReference<HandlerThread> threadWeakReference = asyncQueryHandler.getThreadWeakReference();
                    Log.w("Fire", "ViewActivity:115行:" + resolver + " :--: " + threadWeakReference);
                    if (resolver == null) {
                        Log.e("Fire", "ViewActivity:116行:" + resolver);
                    } else {
                        ContentResolver contentResolver = resolver.get();
                        if (contentResolver == null) {
                            Log.e("Fire", "ViewActivity:120行:" + contentResolver);
                        }
                    }
                    if (threadWeakReference == null) {

                        Log.e("Fire", "ViewActivity:125行:" + threadWeakReference);
                    } else {
                        HandlerThread handlerThread = threadWeakReference.get();
                        if (handlerThread == null) {
                            Log.e("Fire", "ViewActivity:129行:" + handlerThread);
                        }
                    }

                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCursor != null) {
            Log.w("Fire", "ViewActivity:onDestroy:" + mCursor.isClosed());
            new Thread(() -> {
                while (!mCursor.isClosed()) {
                    Log.w("Fire", "ViewActivity:onDestroy:-------");
                }
            }).start();
        }
    }
}
