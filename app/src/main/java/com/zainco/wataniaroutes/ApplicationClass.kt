package com.zainco.wataniaroutes

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.zainco.wataniaroutes.javakoin.start

class ApplicationClass : Application() {

    lateinit var localeManager: LocaleManager
    override fun attachBaseContext(base: Context) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager.setLocale())
    }

    override fun onCreate() {
        super.onCreate()
        start(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(localeManager.getLanguage())
    }
}