package com.zainco.wataniaroutes.javakoin

import android.app.Application
import org.koin.android.ext.koin.with
import org.koin.standalone.StandAloneContext

fun start(application: Application) {
    StandAloneContext.startKoin(
        listOf(
            localModule
        )
    ) with application
}