package com.gusi.androidx.module.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.util.LogWriter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;

import java.io.PrintWriter;

public class FragmentActivity extends BaseActivity {

    private static final String TAG = "Fire_FragmentActivity";

    @Override
    protected int getLayout() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initInject() {

    }

    public void addFragment(View view) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Log.w(TAG, "addFragment: " + supportFragmentManager);
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        BlankFragment blankFragment = BlankFragment.newInstance("Add");
        transaction.add(R.id.fl_add, blankFragment);
        TargetFragment targetFragment = TargetFragment.newInstance();
        transaction.add(R.id.fl_replace, targetFragment);
        transaction.commit();
    }

    public void replaceFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_add, BlankFragment.newInstance("Replace"));
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

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dump("Ylw", null, new PrintWriter(new LogWriter("Ylw")), null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, Log.getStackTraceString(new Throwable("onCreate")));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, Log.getStackTraceString(new Throwable("onStart")));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, Log.getStackTraceString(new Throwable("onResume")));
    }
}