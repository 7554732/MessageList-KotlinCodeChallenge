package com.fomichev.messagelist_kotlincodechallenge

import android.app.Application
import com.fomichev.messagelist_kotlincodechallenge.network.InputFiles
import com.fomichev.messagelist_kotlincodechallenge.network.NetworkInputDataManager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MLApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)


    override fun onCreate() {
        super.onCreate()
//        NetworkInputDataManager.setInputFiles(InputFiles(files = listOf("01.json")))
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
        }
    }
}
