package com.kjh.dietmanagement.view.common

import android.app.Application

class Application : Application() {

    companion object {
        lateinit var preferences: PreferenceUtil
    }

    override fun onCreate() {
        preferences = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}