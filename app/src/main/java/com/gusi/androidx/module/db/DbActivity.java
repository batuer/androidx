package com.gusi.androidx.module.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.TimeUtils;
import com.gusi.androidx.R;

import java.util.Random;

public class DbActivity extends Activity {

    private static final String TAG = "Fire";
    private ListView mListView;
    private BaseAdapter mBaseAdapter;
    //    private List<String> mList;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        mListView = findViewById(R.id.lv);
        mBaseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mCursor == null || mCursor.isClosed() ? 0 : mCursor.getCount();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView textView = (TextView) convertView;
                mCursor.moveToPosition(position);
                long id = mCursor.getLong(mCursor.getColumnIndex("_id"));
                String data1 = mCursor.getString(mCursor.getColumnIndex("data1"));
                String data4 = mCursor.getString(mCursor.getColumnIndex("data4"));
                String result = String.format("id = %s, data1 = %s, data4 = %s",
                        id, data1, data4);
                textView.setText(position + " : " + result);
                return convertView;
            }
        };
        mListView.setAdapter(mBaseAdapter);
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
        Log.e("Fire", "DbActivity:84行:" + cursor.getCount());
        cursor.moveToFirst();

    }

    //CREATE TABLE IF NOT EXISTS "Student" ("_id" INTEGER PRIMARY KEY ,"NAME" TEXT,"BIRTH" TEXT,"SEX" boolean,"AGE"
    // INTEGER,"INTRO" TEXT);
    public void addStudent(View view) {
        ContentValues contentValues = new ContentValues();
        String name = null;
        contentValues.put("NAME", (String) null);
        contentValues.put("BIRTH", TimeUtils.getNowString());
        contentValues.put("SEX", true);
        contentValues.put("AGE", new Random().nextInt(100) + 1);
        contentValues.put("INTRO", "INTRO:");
        DBManger.getInstance().insert("Student", contentValues);
    }

    public void delStudent(View view) {
        DBManger.getInstance().delete("Student", "INTRO = ?", new String[]{"INTRO:"});
    }

    public void updateStudent(View view) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("BIRTH", TimeUtils.getNowString());
//        DBManger.getInstance().update("Student", contentValues, null, null);
        int cursorCount = -3;
        if (mCursor != null) {
            if (mCursor.isClosed()) {
                cursorCount = -2;
            } else {
                cursorCount = mCursor.getCount();
            }
        }
        Log.w("Fire", "DbActivity:161行:" + cursorCount);
        if (mCursor != null) {
            mCursor.close();
        }
         cursorCount = -3;
        if (mCursor != null) {
            if (mCursor.isClosed()) {
                cursorCount = -2;
            } else {
                cursorCount = mCursor.getCount();
            }
        }
        Log.w("Fire", "DbActivity:173行:" + cursorCount);
    }

    //CREATE TABLE IF NOT EXISTS "Student" ("_id" INTEGER PRIMARY KEY ,"NAME" TEXT,"BIRTH" TEXT,"SEX" boolean,"AGE"
    // INTEGER,"INTRO" TEXT);
    public void queryStudent(View view) {
//        Cursor cursor = DBManger.getInstance().query("Student", null, null, null, null, null, null, null);
////        mList = new ArrayList<>(cursor.getCount());
////        while (cursor != null && cursor.moveToNext()) {
////            long id = cursor.getLong(cursor.getColumnIndex("_id"));
////            String name = cursor.getString(cursor.getColumnIndex("NAME"));
////            String birth = cursor.getString(cursor.getColumnIndex("BIRTH"));
////            boolean sex = cursor.getInt(cursor.getColumnIndex("SEX")) == 1 ? true : false;
////            int age = cursor.getInt(cursor.getColumnIndex("AGE"));
////            String result = String.format("id=%s, name=%s, birth=%s, sex=%s, age=%s",
////                    id, name, birth, sex, age);
////            mList.add(result);
////        }
//        cursor.close();
//        mBaseAdapter.notifyDataSetChanged();
        int cursorCount = -3;
        if (mCursor != null) {
            if (mCursor.isClosed()) {
                cursorCount = -2;
            } else {
                cursorCount = mCursor.getCount();
            }
        }
        Log.w("Fire", "DbActivity:183行:" + cursorCount);
        if (mCursor != null) {
            mCursor.requery();
        }
        cursorCount = -3;
        if (mCursor != null) {
            if (mCursor.isClosed()) {
                cursorCount = -2;
            } else {
                cursorCount = mCursor.getCount();
            }
        }
        Log.w("Fire", "DbActivity:195行:" + cursorCount);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void load(View view) {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse("content://com.android.contacts/data"), new String[]{"data1", "data4"
                , "_id"}, null, null, null);
        mCursor = cursor;
        mBaseAdapter.notifyDataSetChanged();
        cursor.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                // requery
                Log.d(TAG, Log.getStackTraceString(new Throwable()));
                Log.w("Fire", "onChanged");
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
                // close
                Log.d(TAG, Log.getStackTraceString(new Throwable()));
                Log.w("Fire", "onInvalidated:" + mCursor.isClosed());
            }
        });
        cursor.registerContentObserver(new ContentObserver(view.getHandler()) {
            @Override
            public boolean deliverSelfNotifications() {
                boolean b = super.deliverSelfNotifications();
                Log.d(TAG, Log.getStackTraceString(new Throwable()));
                Log.w("Fire", "deliverSelfNotifications:" + b);
                return b;
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                int cursorCount = -3;
                if (mCursor != null) {
                    if (mCursor.isClosed()) {
                        cursorCount = -2;
                    } else {
                        cursorCount = mCursor.getCount();
                    }
                }
                // insert, update, delete
                Log.d(TAG, Log.getStackTraceString(new Throwable()));
                Log.w("Fire", "onChange:" + selfChange + " : " + cursorCount);
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                // insert, update, delete
                Log.d(TAG, Log.getStackTraceString(new Throwable()));
                Log.w("Fire", "onChange:" + selfChange + " : " + uri);
            }
        });
    }


    private class MyCursorAdapter extends CursorAdapter {
        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public int getCount() {
            int count = super.getCount();
            Log.w("Fire", "MyCursorAdapter:117行:" + count);
            return count;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            Log.w("Fire", "MyCursorAdapter:124行:" + position);
            return view;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setText(cursor.getString(cursor.getColumnIndex("BIRTH")));
            }
        }
    }
}
