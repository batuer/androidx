package com.gusi.androidx.module.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.gusi.androidx.R;
import com.gusi.androidx.module.lv.ListViewActivity;

public class FragmentActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState != null) {
            Bitmap bitmap = savedInstanceState.getParcelable("Bitmap");
            Log.e("Fire", "FragmentActivity:33行:" + bitmap);
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        }
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

        outState.putByteArray("byte0000000000000000", new byte[1024 * 1024]);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
//        outState.putParcelable("Bitmap", bitmap);

//        outState.putString("onSaveInstanceState", getClass().getName() + "__onSaveInstanceState");
        Log.i("Fire", "FragmentActivity:40行:onSaveInstanceState");
        Log.w("Fire", Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onStop() {
        Log.w("Fire", "FragmentActivity:63行:onStop" );
        super.onStop();
        Log.w("Fire", "FragmentActivity:65行:onStop" );

    }

    public void addFragment(View view) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.fl_add, BlankFragment.newInstance("Add"));
//        transaction.commit();
    }

    public void replaceFragment(View view) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fl_replace, BlankFragment.newInstance("Replace"));
//        transaction.commit();
    }

    public void removeFragment(View view) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        for (Fragment fragment : fragmentManager.getFragments()) {
//            transaction.remove(fragment);
//        }
//        transaction.commit();
    }

    public void turn(View view) {
        startActivity(new Intent(this, ListViewActivity.class));
//        finish();
        isFinishing();
        isDestroyed();
    }
}