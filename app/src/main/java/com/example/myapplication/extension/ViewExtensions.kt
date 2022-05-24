package com.example.myapplication.extension

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun View?.onClick(action: (view: View) -> Unit) {
    this?.let {
        RxView.clicks(it)
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { action(this) }
    }
}