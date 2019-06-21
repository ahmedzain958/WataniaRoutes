package com.zainco.wataniaroutes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import org.koin.java.standalone.KoinJavaComponent


abstract class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(base: Context?) {
        val localeManager: LocaleManager = KoinJavaComponent.get(LocaleManager::class.java)
        super.attachBaseContext(localeManager.setLocale(localeManager.getLanguage()))
    }
}