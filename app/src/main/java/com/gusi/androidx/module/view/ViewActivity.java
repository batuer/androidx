package com.gusi.androidx.module.view;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import com.blankj.utilcode.util.PermissionUtils;
import com.gusi.androidx.R;

/**
 * @author Ylw
 * @since 2019/8/24 23:17
 */
public class ViewActivity extends Activity {
    private static final String TAG = "Fire";
    private String[] permissionList = new String[] { // 申请的权限列表
        Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.READ_CONTACTS};
    private Uri callUri = CallLog.Calls.CONTENT_URI;
    private String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
        , CallLog.Calls.NUMBER// 通话记录的电话号码
        , CallLog.Calls.DATE// 通话记录的日期
        , CallLog.Calls.DURATION// 通话时长
        , CallLog.Calls.TYPE};// 通话类型}
    String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
    }

    public void clickTest(View view) {
        view.requestLayout();
    }

    public void query(View view) {
        if (PermissionUtils.isGranted(permissionList)) {
            getConnect();
        } else {
            PermissionUtils.permission(permissionList).request();
        }
    }

    // 获取联系人
    private void getConnect() {
        String[] projection = {};
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            new String[] {"display_name", "sort_key", "contact_id", "data1"}, null, null, null);
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

    public final @Nullable Cursor query(final @RequiresPermission.Read @NonNull Uri uri, @Nullable String[] projection,
        @Nullable Bundle queryArgs, @Nullable CancellationSignal cancellationSignal) {
        Preconditions.checkNotNull(uri, "uri");

        try {
            Log.w("Fire", "ViewActivity:84行:" + mWrapped);
            if (mWrapped != null) {
                return mWrapped.query(uri, projection, queryArgs, cancellationSignal);
            }
        } catch (RemoteException e) {
            Log.e("Fire", "ViewActivity:89行:" + e.toString());
            return null;
        }

        IContentProvider unstableProvider = acquireUnstableProvider(uri);
        Log.w("Fire", "ViewActivity:94行:" + unstableProvider);
        if (unstableProvider == null) {
            return null;
        }
        IContentProvider stableProvider = null;
        Cursor qCursor = null;
        try {
            long startTime = SystemClock.uptimeMillis();

            ICancellationSignal remoteCancellationSignal = null;
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
                remoteCancellationSignal = unstableProvider.createCancellationSignal();
                cancellationSignal.setRemote(remoteCancellationSignal);
            }
            try {
                qCursor = unstableProvider.query(mPackageName, uri, projection, queryArgs, remoteCancellationSignal);
                Log.w("Fire", "ViewActivity:111行:-----------------------" );
            } catch (DeadObjectException e) {
                // The remote process has died... but we only hold an unstable
                // reference though, so we might recover!!! Let's try!!!!
                // This is exciting!!1!!1!!!!1
                unstableProviderDied(unstableProvider);
                stableProvider = acquireProvider(uri);
                if (stableProvider == null) {
                    return null;
                }
                qCursor = stableProvider.query(mPackageName, uri, projection, queryArgs, remoteCancellationSignal);
            }
            Log.w("Fire", "ViewActivity:123行:" + qCursor);
            if (qCursor == null) {
                return null;
            }

            // Force query execution. Might fail and throw a runtime exception here.
            qCursor.getCount();
            long durationMillis = SystemClock.uptimeMillis() - startTime;
            maybeLogQueryToEventLog(durationMillis, uri, projection, queryArgs);

            // Wrap the cursor object into CursorWrapperInner object.
            final IContentProvider provider = (stableProvider != null) ? stableProvider : acquireProvider(uri);
            final CursorWrapperInner wrapper = new CursorWrapperInner(qCursor, provider);
            stableProvider = null;
            qCursor = null;
            Log.w("Fire", "ViewActivity:138行:" + wrapper);
            return wrapper;
        } catch (RemoteException e) {
            // Arbitrary and not worth documenting, as Activity
            // Manager will kill this process shortly anyway.
            return null;
        } finally {
            if (qCursor != null) {
                qCursor.close();
            }
            if (cancellationSignal != null) {
                cancellationSignal.setRemote(null);
            }
            if (unstableProvider != null) {
                releaseUnstableProvider(unstableProvider);
            }
            if (stableProvider != null) {
                releaseProvider(stableProvider);
            }
        }
    }

}
