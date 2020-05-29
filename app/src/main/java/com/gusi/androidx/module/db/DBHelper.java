package com.gusi.androidx.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author Ylw
 * @since 2020/5/24 17:44
 */
public class DBHelper extends SQLiteOpenHelper {
    // 数据库默认名字
    public static final String DB_NAME = "Ylw.db";

    public DBHelper(@Nullable Context context, int version) {
        super(context, DB_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table table1 (" +
                " _byte byte," +
                " _long long," +
                " _text text," +
                " _short short," +
                " _int int," +
                " _float float," +
                " _double double," +
                " _boolean boolean," +
                " _blob blob" +
                ")");
        boolean ifNotExists = true;
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"Student\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," +
                "\"NAME\" TEXT," +
                "\"BIRTH\" TEXT," +
                "\"SEX\" boolean," +
                "\"AGE\" INTEGER," +
                "\"INTRO\" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
