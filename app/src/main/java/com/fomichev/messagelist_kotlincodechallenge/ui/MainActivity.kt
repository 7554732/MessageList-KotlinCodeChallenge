package com.fomichev.messagelist_kotlincodechallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fomichev.messagelist_kotlincodechallenge.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate")
    }
}