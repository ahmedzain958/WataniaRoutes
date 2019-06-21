package com.zainco.wataniaroutes

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.LocaleList
import android.preference.PreferenceManager
import android.text.TextUtils
import java.util.*

class LocaleManager(private val context: Context) {
    companion object {
        const val LANGUAGE_ARABIC = "ar"
    }

    private lateinit var prefs: SharedPreferences

    fun setLocale(language: String = LANGUAGE_ARABIC): Context {
        setLanguage(language)
        return updateResources(getLanguage())
    }

    private fun updateResources(language: String): Context {
        var context = context
        if (TextUtils.isEmpty(language)) return context
        val locale = Locale(language)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            configuration.locales = localeList
        } else {
            Locale.setDefault(locale)
        }
        context = context.createConfigurationContext(configuration)
        return context
    }

    fun getLanguage(): String {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString("language", LANGUAGE_ARABIC)
    }

    fun setLanguage(language: String) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString("language", language).commit()
    }
}