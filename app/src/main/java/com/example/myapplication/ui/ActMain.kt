package com.example.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R


class ActMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        setFragment()
    }

    private fun setFragment() {
        val frgFind = FrgSearch.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fLayout, frgFind, "")
            .commit()
    }
}