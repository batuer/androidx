package com.gusi.androidx.module.view;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import androidx.annotation.Nullable;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
//        WindowManager.LayoutParams attributes = getWindow().getAttributes();

    }

    public void clickTest(View view) {
        view.requestLayout();
    }

    private boolean b;

    public void query(View view) {
        getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader cursorLoader = new CursorLoader(ViewActivity.this,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{"display_name", "sort_key", "contact_id", "data1"}, null, null, null);
                Log.w("Fire", "ViewActivity:onCreateLoader:" + cursorLoader + " , " + Thread.currentThread());
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                mCursor = cursor;
                Log.w("Fire_" + cursor, Log.getStackTraceString(new Throwable()));
                if (!b) {
                    b = true;
//                    new MyCursorAdapter(ViewActivity.this, cursor);
                }


                while (cursor.moveToNext()) {
                    // 读取通讯录的姓名
                    String name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    // 读取通讯录的号码
                    String number =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    Log.i(TAG, "获取的通讯录是： " + name + "\n" + " number : " + number);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                Log.w("Fire", "ViewActivity:onLoaderReset:" + loader + " , " + Thread.currentThread());
            }
        });


//        view.invalidate();
//        if (PermissionUtils.isGranted(permissionList)) {
//            getConnect();
//        } else {
//            PermissionUtils.permission(permissionList).request();
//        }
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

    int a = 0;

    public void handleThread(View view) {
        mHandlerThread = new HandlerThread("Ylw----Ylw: " + a++);
        mHandlerThread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                long l = System.currentTimeMillis();
                while ((System.currentTimeMillis() - l) < 5000) {
                    Log.w("Fire", "ViewActivity:86行:" + l);
                }
            }
        });
        thread1.setName("Ylw===Ylw: " + a);
        thread1.start();
    }

    public void release(View view) {
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.w("Fire", "ViewActivity:178行:" + mCursor.isClosed());
            }
        }, 3000);

    }
}
