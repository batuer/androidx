package com.gusi.androidx.module.constraintlayout

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @author  Ylw
 * @since 2019/7/2 22:37
 */
class CircularRevealHelper @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintHelper(context, attrs, defStyleAttr) {

    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val views = getViews(container)
            val i = arrayOf(1, 2)
            for (temp in i) {
                Log.w("Fire", "==========:" + temp)
            }
            Log.w("Fire", "---------------" + views)
            for (view in views) {
                Log.e("Fire", "---------------")
                val anim = ViewAnimationUtils.createCircularReveal(view, view.width / 2, view.height / 2, 0f,
                        Math.hypot((view.height / 2).toDouble(), (view.width / 2).toDouble()).toFloat())
                anim.duration = 3000
                anim.start()
            }
        }
    }
}