package com.zainco.wataniaroutes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zainco.wataniaroutes.SelectionActivity.Companion.SELECTION
import kotlinx.android.synthetic.main.activity_add_project.*

class AddProjectActivity : AppCompatActivity() {
    companion object {
        const val ROUTE_SELECTION_CODE = 300
        const val INVESTOR_SELECTION_CODE = 301
        const val LOCATION_SELECTION_CODE = 302
        const val RENT_TYPE_SELECTION_CODE = 303
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
        textRoute.setOnClickListener {
            val intent = Intent(this, SelectionActivity::class.java)
            intent.putExtra(SELECTION, ROUTE_SELECTION_CODE)
            startActivityForResult(intent, ROUTE_SELECTION_CODE)
        }
        textInvestor.setOnClickListener {
            val intent = Intent(this, SelectionActivity::class.java)
            intent.putExtra(SELECTION, INVESTOR_SELECTION_CODE)
            startActivityForResult(intent, INVESTOR_SELECTION_CODE)
        }
        textLocation.setOnClickListener {
            val intent = Intent(this, SelectionActivity::class.java)
            intent.putExtra(SELECTION, LOCATION_SELECTION_CODE)
            startActivityForResult(intent, LOCATION_SELECTION_CODE)
        }
        textRentType.setOnClickListener {
            val intent = Intent(this, SelectionActivity::class.java)
            intent.putExtra(SELECTION, RENT_TYPE_SELECTION_CODE)
            startActivityForResult(intent, RENT_TYPE_SELECTION_CODE)
        }

        button.setOnClickListener {
            val project = Project(
                editAnnualRaise.text.toString().toDouble(),
                editArea.text.toString(),
                editEndDate.text.toString(),
                textInvestor.text.toString(),
                textLocation.text.toString(),
                editNotes.text.toString(),
                editPeriod.text.toString(),
                editPrice.text.toString().toInt(),
                editProject.text.toString(),
                textRentType.text.toString(),
                editRentValue.text.toString().toLong(),
                textRoute.text.toString(),
                editStartDate.text.toString()
            )


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ROUTE_SELECTION_CODE -> {
                    textRoute.text = data!!.getStringExtra(SELECTION)
                }
                INVESTOR_SELECTION_CODE -> {
                    textInvestor.text = data!!.getStringExtra(SELECTION)
                }
                LOCATION_SELECTION_CODE -> {
                    textLocation.text = data!!.getStringExtra(SELECTION)
                }
                RENT_TYPE_SELECTION_CODE -> {
                    textRentType.text = data!!.getStringExtra(SELECTION)
                }
            }
        }
    }
}
