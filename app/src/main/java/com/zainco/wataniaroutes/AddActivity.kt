package com.zainco.wataniaroutes

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.uctd_toolbar.*

class AddActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        initToolbar(uctdToolbar, getString(R.string.add_project), withBack = true)
        cardAddProject.setOnClickListener {
            val intent = Intent(this, AddProjectActivity::class.java)
            startActivity(intent)
        }
        cardAddRoute.setOnClickListener {
            val intent = Intent(this, RouteActivity::class.java)
            startActivity(intent)
        }
        cardAddLocation.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
        }
        cardAddInvestor.setOnClickListener {
            val intent = Intent(this, InvestorActivity::class.java)
            startActivity(intent)
        }
        cardAddRentRate.setOnClickListener {
            val intent = Intent(this, RentRateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
