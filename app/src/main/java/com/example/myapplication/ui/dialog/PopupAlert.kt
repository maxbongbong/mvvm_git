package com.example.myapplication.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.extension.onClick
import kotlinx.android.synthetic.main.popup_alert.*

class PopupAlert(context: Context) : Dialog(context, R.style.Theme_MyApplication_Dialog) {

    private var title: String? = null
    private var message: String? = null
    private var leftBtnName: String? = null
    private var rightBtnName: String? = null
    private var leftClickListener: View.OnClickListener? = null
    private var rightClickListener: View.OnClickListener? = null
    private var dimAmount = 0.5f
    var mCancelable = false

    /**
     * 기본
     */
    constructor(
        context: Context,
        title: String?,
        message: String?
    ) : this(context) {

        this.title = title
        this.message = message
    }

    constructor(
        context: Context,
        title: String?,
        message: String?,
        leftBtnName: String?,
        rightBtnName: String?,
        leftClickListener: View.OnClickListener?,
        rightClickListener: View.OnClickListener?
    ) : this(context, title, message) {

        this.title = title
        this.leftBtnName = leftBtnName
        this.leftClickListener = leftClickListener
        this.rightBtnName = rightBtnName
        this.rightClickListener = rightClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCancelable(mCancelable)

        val lpWindow = WindowManager.LayoutParams()
        lpWindow.dimAmount = dimAmount
        window!!.attributes = lpWindow

        setContentView(R.layout.popup_alert)

        // 타이틀 없는 경우
        if (title.isNullOrEmpty()) {

            llNormal.visibility = View.GONE
            llSingle.visibility = View.VISIBLE

            tvMessageSingle.text = message

        } else {

            llNormal.visibility = View.VISIBLE
            llSingle.visibility = View.GONE

            tvTitle.text = title
            tvMessage.text = message
        }

        // left btn
        if (leftClickListener != null && leftBtnName != null) {

            btnLeft.onClick {
                leftClickListener?.onClick(btnLeft)
            }
            leftBtnName?.let { btnLeft.text = leftBtnName }

            btnLeft.visibility = View.VISIBLE

        } else {

            btnLeft.visibility = View.GONE
        }

        // right btn
        btnRight.onClick {

            if (rightClickListener == null) {
                dismiss()
            } else {
                rightClickListener?.onClick(btnRight)
            }
        }

        rightBtnName?.let {
            btnRight.text = it
        }
    }
}