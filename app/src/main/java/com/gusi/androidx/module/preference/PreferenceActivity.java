package com.gusi.androidx.module.preference;

import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.gusi.androidx.R;

import java.util.List;

/**
 * @author Ylw
 * @since 2019/7/2 23:49
 */
public class PreferenceActivity extends android.preference.PreferenceActivity implements
        Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference1);
    }

    @Override
    public void onHeaderClick(Header header, int position) {
        super.onHeaderClick(header, position);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.w("Fire", "PreferenceActivity:38行:" + preference.toString());
        Log.e("Fire", "PreferenceActivity:39行:" + newValue);
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Log.w("Fire", "PreferenceActivity:42行:" + preference.toString());
        return false;
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
    }
}
