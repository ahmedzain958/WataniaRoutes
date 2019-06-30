package com.zainco.wataniaroutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SelectionActivity : AppCompatActivity() {
    companion object {
        const val SELECTED_ROUTE = "selected_route"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
    }
}
