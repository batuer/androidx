package com.gusi.androidx.module.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.Utils;

/**
 * @author Ylw
 * @since 2020/5/24 17:48
 */
public final class DBManger {
    private static volatile DBManger sDBManger;
    private final Context mContext;
    // 操作表的对象，进行增删改查
    private final SQLiteDatabase mWritableDatabase;

    private DBManger(Context context) {
        //no instance
        this.mContext = context;
        DBHelper dbHelper = new DBHelper(context, 1);
        this.mWritableDatabase = dbHelper.getWritableDatabase();
    }

    public static DBManger getInstance() {
        if (sDBManger == null) {
            synchronized (DBManger.class) {
                if (sDBManger == null) {
                    sDBManger = new DBManger(Utils.getApp());
                }
            }
        }
        return sDBManger;
    }

    public SQLiteDatabase getWritableDatabase() {
        return mWritableDatabase;
    }

    public void insert(String table, ContentValues values) {
        mWritableDatabase.insert(table, null, values);
    }

    public void delete(String table, String whereClause, String[] whereArgs) {
        mWritableDatabase.delete(table, whereClause, whereArgs);
    }

    public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        mWritableDatabase.update(table, values, whereClause, whereArgs);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mWritableDatabase.rawQuery(sql, selectionArgs);
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit) {
        return mWritableDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

}
