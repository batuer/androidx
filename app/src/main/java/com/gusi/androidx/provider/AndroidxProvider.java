package com.gusi.androidx.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;

import androidx.annotation.Nullable;

public class AndroidxProvider extends ContentProvider {
    private static final String TAG = "Ylw_AndroidxProvider";

    public static final String AUTHORITY = "com.gusi.androidx.provider";
    // 表名
    public static final String USER_TABLE_NAME = "user";
    public static final int User_Code = 1;


    // UriMatcher类使用:在ContentProvider 中注册URI
    private static final UriMatcher mMatcher;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // 初始化
        mMatcher.addURI(AUTHORITY, "user", User_Code);
    }

    private SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(getContext(), "gusi.db", null, 2);
        mDatabase = databaseOpenHelper.getWritableDatabase();
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = getTableName(uri);
        int delete = mDatabase.delete(tableName, selection, selectionArgs);
        Log.i(TAG, "delete: " + uri + ":" + Binder.getCallingPid());
        getContext().getContentResolver().notifyChange(uri, null);
        return delete;
    }

    @Override
    public String getType(Uri uri) {
        return "";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTableName(uri);
        long insert = mDatabase.insert(tableName, null, values);
        Log.i(TAG, "insert: " + insert + ":" + uri + ":" + values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String tableName = getTableName(uri);
        Log.i(TAG, "query: " + uri + ":" + Binder.getCallingPid());
        return mDatabase.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String tableName = getTableName(uri);
        int update = mDatabase.update(tableName, values, selection, selectionArgs);
        Log.i(TAG, "update: " + uri + ":" + Binder.getCallingPid());
        getContext().getContentResolver().notifyChange(uri, null);
        return update;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case User_Code:
                tableName = USER_TABLE_NAME;
                break;
            default:
                throw new RuntimeException("not match uri =" + uri);
        }
        return tableName;
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {


        public DatabaseOpenHelper(@Nullable Context context, @Nullable String name,
                                  @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "onCreate: " + db.getVersion() + ":" + db.getPath());
            // 创建两个表格:用户表
            db.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT DEFAULT '', " +
                    "address TEXT ," +
                    "sex INTEGEER DEFAULT '0'" +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "onUpgrade: oldVersion= " + oldVersion + " ,newVersion=" + newVersion);
            if (oldVersion <= 1 && newVersion >= 2) {
                db.execSQL("ALTER TABLE " + USER_TABLE_NAME + " ADD 'auto' TEXT DEFAULT '0';");
            }
        }
    }
}