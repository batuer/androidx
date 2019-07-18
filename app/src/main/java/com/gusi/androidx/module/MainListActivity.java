package com.gusi.androidx.module;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ylw
 * @since 2019/6/28 23:40
 */
public class MainListActivity extends ListActivity {
    private static final String FILTER = "Filter";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<ActivityInfo> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, initData()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                int labelRes = ((ActivityInfo) getItem(position)).labelRes;
                tv.setText(labelRes);
                return tv;
            }
        };
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ActivityInfo activityInfo = (ActivityInfo) getListAdapter().getItem(position);
        ClassLoader classLoader = getClassLoader();
        try {
            Class<?> aClass = classLoader.loadClass(activityInfo.name);
            startActivity(new Intent(this, aClass));
        } catch (ClassNotFoundException e) {
        }
    }

    private List<ActivityInfo> initData() {
        List<ActivityInfo> list = new ArrayList<>();
        PackageManager pm = getPackageManager();
        try {
            String packageName = getPackageName();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activityInfo : packageInfo.activities) {
                if (activityInfo.labelRes <= 0 || !activityInfo.name.contains(getPackageName())) {
                    continue;
                }
                list.add(activityInfo);
            }

            Collections.sort(list, (o1, o2) -> o1.name.substring(o1.name.lastIndexOf(".") + 1)
                    .replace("Activity", "")
                    .compareTo(o2.name.substring(o2.name.lastIndexOf(".") + 1)
                            .replace("Activity", "")));
        } catch (Exception e) {
        }
        return list;
    }
}
