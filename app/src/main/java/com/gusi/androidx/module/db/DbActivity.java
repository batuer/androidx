package com.gusi.androidx.module.db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.blankj.utilcode.util.TimeUtils;
import com.gusi.androidx.R;

import java.util.Random;

public class DbActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ListView listView = findViewById(R.id.lv);

    }

    //create table table1 ( _byte byte, _long long, _text text, _short short, _int int, _float float, _double double,
    // _boolean boolean, _blob blob)
    public void addTable1(View view) {
        ContentValues contentValues = new ContentValues();
        byte _byte = Byte.MAX_VALUE;
        contentValues.put("_byte", _byte);
        contentValues.put("_long", System.currentTimeMillis());
        contentValues.put("_text", TimeUtils.getNowString());
        short _short = Short.MAX_VALUE;
        contentValues.put("_short", _short);
        int _int = Integer.MAX_VALUE;
        contentValues.put("_int", _int);
        float _float = Float.MAX_VALUE;
        contentValues.put("_float", _float);
        double _double = Double.MAX_VALUE;
        contentValues.put("_double", _double);
        boolean _boolean = true;
        contentValues.put("_boolean", _boolean);
        byte[] _byteArr = {Byte.MIN_VALUE, Byte.MAX_VALUE};
        contentValues.put("_blob", _byteArr);
        DBManger.getInstance().insert("table1", contentValues);
    }

    public void delTable1(View view) {
        DBManger.getInstance().delete("table1", "_int = ?", new String[]{Integer.MAX_VALUE + ""});
    }

    public void updateTable1(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_text", TimeUtils.getNowString());
        DBManger.getInstance().update("table1", contentValues, "_text = ?", new String[]{TimeUtils.getNowString()});
    }

    public void queryTable1(View view) {
        Cursor cursor = DBManger.getInstance().query("table1", null, null, null, null, null, null, null);
        String result = "";
        while (cursor != null && cursor.moveToNext()) {
            byte _byte = (byte) cursor.getShort(cursor.getColumnIndex("_byte"));
            long _long = cursor.getLong(cursor.getColumnIndex("_long"));
            String _text = cursor.getString(cursor.getColumnIndex("_text"));
            short _short = cursor.getShort(cursor.getColumnIndex("_short"));
            int _int = cursor.getInt(cursor.getColumnIndex("_int"));
            float _float = cursor.getFloat(cursor.getColumnIndex("_float"));
            double _double = cursor.getDouble(cursor.getColumnIndex("_double"));
            boolean _boolean = cursor.getInt(cursor.getColumnIndex("_boolean")) == 1 ? true : false;
            byte[] _byteArr = cursor.getBlob(cursor.getColumnIndex("_blob"));

            result = String.format("_byte = %s, _long = %s, _text = %s, _short = %s, _int = %s, _float = %s, _double" +
                            " = %s, _boolean = %s, _byteArr = %s",
                    _byte, _long, _text, _short, _int, _float, _double, _boolean, _byteArr) ;
            Log.w("Fire", "DbActivity:69行:" + result);
        }
    }

    //CREATE TABLE IF NOT EXISTS "Student" ("_id" INTEGER PRIMARY KEY ,"NAME" TEXT,"BIRTH" TEXT,"SEX" boolean,"AGE"
    // INTEGER,"INTRO" TEXT);
    public void addStudent(View view) {
        ContentValues contentValues = new ContentValues();
        String name = null;
        contentValues.put("NAME", (String)null);
        contentValues.put("BIRTH", TimeUtils.getNowString());
        contentValues.put("SEX", true);
        contentValues.put("AGE", new Random().nextInt(100) + 1);
        contentValues.put("INTRO", "INTRO:");
        DBManger.getInstance().insert("Student", contentValues);
    }

    public void delStudent(View view) {
        DBManger.getInstance().delete("Student", "NAME = ?", new String[]{"name"});
    }

    public void updateStudent(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("BIRTH", TimeUtils.getNowString());
        DBManger.getInstance().update("Student", contentValues, "BIRTH = ?", new String[]{TimeUtils.getNowString()});
    }

    //CREATE TABLE IF NOT EXISTS "Student" ("_id" INTEGER PRIMARY KEY ,"NAME" TEXT,"BIRTH" TEXT,"SEX" boolean,"AGE"
    // INTEGER,"INTRO" TEXT);
    public void queryStudent(View view) {
        Cursor cursor = DBManger.getInstance().query("Student", null, null, null, null, null, null, null);
        String result = "";
        while (cursor != null && cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("NAME"));
            Log.w("Fire", "DbActivity:108行:" + (name == null) + " : " + name);
            String birth = cursor.getString(cursor.getColumnIndex("BIRTH"));
            boolean sex = cursor.getInt(cursor.getColumnIndex("SEX")) == 1 ? true : false;
            int age = cursor.getInt(cursor.getColumnIndex("AGE"));
            result = String.format("id = %s, name = %s, birth = %s, sex = %s, age = %s",
                    id, name, birth, sex, age);
            Log.w("Fire", "DbActivity:69行:" + result);
        }
    }

    private class MyCursorAdapter extends CursorAdapter{
        public MyCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return null;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

        }
    }
}
