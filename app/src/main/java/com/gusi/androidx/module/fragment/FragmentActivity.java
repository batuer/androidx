package com.gusi.androidx.module.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gusi.androidx.R;

public class FragmentActivity extends androidx.fragment.app.FragmentActivity {

    private static final String TAG = "Fire_FragmentActivity";
    private BlankFragment mBlankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }


    public void addFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mBlankFragment = BlankFragment.newInstance("Add");
        transaction.add(R.id.fl_add, mBlankFragment);
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

    public void turn(View view) {

    }

}