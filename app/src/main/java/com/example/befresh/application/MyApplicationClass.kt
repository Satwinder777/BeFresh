package com.example.befresh.application

import android.app.Application
import com.example.befresh.utils.CommonMethod
import com.example.befresh.utils.SharedPref
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
        CommonMethod.invoke(applicationContext)
        SharedPref(applicationContext)
    }

    companion object {
        private lateinit var mInstance: MyApplicationClass
        @Synchronized
        fun getInstance(): MyApplicationClass {
            return mInstance
        }
    }
}