package com.zainco.wataniaroutes.javakoin

import android.annotation.SuppressLint
import com.zainco.wataniaroutes.LocaleManager
import org.koin.dsl.module.module


@SuppressLint("CommitPrefEdits")
@JvmField
val localModule = module {
    single { LocaleManager(get()) }
}
