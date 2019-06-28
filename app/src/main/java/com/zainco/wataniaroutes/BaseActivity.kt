package com.zainco.wataniaroutes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.koin.java.standalone.KoinJavaComponent


abstract class BaseActivity : AppCompatActivity() {

    protected fun initToolbar(
        toolbar: Toolbar,
        title: String,
        withBack: Boolean = false
    ) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(withBack)
        supportActionBar!!.setDisplayHomeAsUpEnabled(withBack)
        supportActionBar!!.title = title
    }
    override fun attachBaseContext(base: Context?) {
        val localeManager: LocaleManager = KoinJavaComponent.get(LocaleManager::class.java)
        super.attachBaseContext(localeManager.setLocale(localeManager.getLanguage()))
    }
}