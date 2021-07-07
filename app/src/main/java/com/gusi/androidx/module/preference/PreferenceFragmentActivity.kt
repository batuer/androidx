package com.gusi.androidx.module.preference

import android.os.Bundle
import android.preference.PreferenceActivity


class PreferenceFragmentActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingFragment.newInstance())
                .commit()
    }

    override fun onBuildHeaders(target: MutableList<Header>?) {
        super.onBuildHeaders(target)
//        loadHeadersFromResource(R.xml.preference_header, target);
    }
}
