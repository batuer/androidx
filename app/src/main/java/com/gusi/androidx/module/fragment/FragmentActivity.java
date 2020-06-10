package com.gusi.androidx.module.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gusi.androidx.R;
import com.gusi.androidx.module.lv.ListViewActivity;

public class FragmentActivity extends androidx.fragment.app.FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ImageView imageView = findViewById(R.id.iv);
        Log.w("Fire", "FragmentActivity:27行:onCreate()" + this);
        if (savedInstanceState != null) {
            Bitmap bitmap = savedInstanceState.getParcelable("Bitmap");
            imageView.setImageBitmap(bitmap);
//            Log.e("Fire", "FragmentActivity:33行:" + bitmap);
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);
//            Log.w("Fire", "FragmentActivity:33行:" + bitmap.getByteCount());
            imageView.setImageBitmap(bitmap);
        }
        View view = findViewById(R.id.list);
        view.setBackground(null);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            for (String key : savedInstanceState.keySet()) {
                Log.i("Fire",
                        "FragmentActivity:23行:onRestoreInstanceState:key = " + key + " , value = " + savedInstanceState.get(key));
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putByteArray("byte0000000000000000", new byte[1024]);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
//        outState.putParcelable("Bitmap", bitmap);

//        outState.putString("onSaveInstanceState", getClass().getName() + "__onSaveInstanceState");
        Log.i("Fire", "FragmentActivity:40行:onSaveInstanceState");
        Log.w("Fire", Log.getStackTraceString(new Throwable()));
    }


    public void addFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_add, BlankFragment.newInstance("Add"));
        transaction.commit();
    }

    public void replaceFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_replace, BlankFragment.newInstance("Replace"));
        transaction.commit();
    }

    public void removeFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragmentManager.getFragments()) {
            transaction.remove(fragment);
        }
        transaction.commit();
    }

    public void turn(View view) {
        startActivity(new Intent(this, ListViewActivity.class));
//        finish();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.w("Fire_" + Thread.currentThread(), "FragmentActivity:101行:finalize()" + this);
        Thread.sleep(61000);
    }
}