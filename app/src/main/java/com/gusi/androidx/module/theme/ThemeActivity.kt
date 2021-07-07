package com.gusi.androidx.module.theme

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gusi.androidx.R
import kotlinx.android.synthetic.main.activity_theme.*


class ThemeActivity : AppCompatActivity() {
    companion object {
        private var sCurrentTheme = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 这句必须放在 setContentView() 之前, 否则不生效.
        // 一般的做法是把这句话放在你的 BaseActivity 里面.
        if (sCurrentTheme != -1) {
            setTheme(sCurrentTheme)
        }
        setContentView(R.layout.activity_theme)
        btn_default.setOnClickListener { v: View? -> changeTheme(R.style.theme_default) }
        btn_dark.setOnClickListener { v: View? -> changeTheme(R.style.theme_dark) }
        btn_light.setOnClickListener { v: View? -> changeTheme(R.style.theme_light) }
    }

    private fun changeTheme(theme: Int) {
        sCurrentTheme = theme
        recreate()
    }
}
