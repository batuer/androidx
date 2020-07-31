package com.gusi.androidx.module.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.gusi.androidx.module.db.DBHelper;
import com.gusi.androidx.module.db.DBManger;

public class MyContentProvider extends ContentProvider {
    private static final String TAG = "Fire_Provider";

    public static final String AUTHORITY = "com.gusi.provider";

    public MyContentProvider() {

    }

    // UriMatcher类使用:在ContentProvider 中注册URI
    private static final UriMatcher mMatcher;

    private static final int STUDENT_CODE = 1;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // 初始化
        mMatcher.addURI(AUTHORITY, "student", STUDENT_CODE);
        // 若URI资源路径 = content://cn.scu.myprovider/user ，则返回注册码User_Code
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.w(TAG, "delete: " + uri + " : " + selection + " : " + selectionArgs);
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        Log.w(TAG, "delete: " + uri);
        return "";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String table = getTableName(uri);
        // 向该表添加数据
        DBManger.getInstance().getWritableDatabase().insert(table, null, values);
        // 当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
        getContext().getContentResolver().notifyChange(uri, null);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        return uri;
    }

    @Override
    public boolean onCreate() {
        Log.w(TAG, "onCreate: " + getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.w(TAG, Thread.currentThread().getName() + " :query: " + uri + " : " + selection);
        if (!TextUtils.isEmpty(selection)) {
            int time = Integer.parseInt(selection);
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < time * 1000) {
            }
        }

        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String table = getTableName(uri);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        // 查询数据
        Cursor query = DBManger.getInstance().getWritableDatabase().query(table, projection, selection, selectionArgs
                , null,
                null, sortOrder, null);
        Log.e(TAG, Thread.currentThread().getName() + " :query: " + uri + " : " + selection);
        return query;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    /**
     * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case STUDENT_CODE:
                tableName = DBHelper.STUDENT_TABLE_NAME;
                break;
        }
        return tableName;
    }
}
