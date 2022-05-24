package com.example.myapplication.basic

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BasicFragment : Fragment() {

    private var mToast: Toast? = null

    /**
     * Toast Message
     */
    open fun showToastMessage(message: String?) {

        activity?.let {
            mToast?.cancel()
            mToast = Toast.makeText(it, message, Toast.LENGTH_SHORT)
            mToast?.setGravity(Gravity.BOTTOM, 0, pxFromDp(it, 15f).toInt())
            mToast?.show()
        }
    }

    open fun pxFromDp(context: Context, dp: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dp * scale
    }
}