package com.zainco.wataniaroutes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zainco.wataniaroutes.SelectionActivity.Companion.SELECTED_ROUTE
import kotlinx.android.synthetic.main.activity_add_project.*

class AddProjectActivity : AppCompatActivity() {
    companion object {
        const val ROUTE_SELECTION = 300
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
        textRoute.setOnClickListener {
            Intent(this, SelectionActivity::class.java).also {
                it.putExtra(SELECTED_ROUTE, "")
                startActivityForResult(intent, ROUTE_SELECTION)
            }
        }
        textInvestor.setOnClickListener {
            Intent(this, SelectionActivity::class.java).also {
                it.putExtra(SELECTED_ROUTE, "")
                startActivityForResult(intent, ROUTE_SELECTION)
            }
        }
        textLocation.setOnClickListener {
            Intent(this, SelectionActivity::class.java).also {
                it.putExtra(SELECTED_ROUTE, "")
                startActivityForResult(intent, ROUTE_SELECTION)
            }
        }
        textRentType.setOnClickListener {
            Intent(this, SelectionActivity::class.java).also {
                it.putExtra(SELECTED_ROUTE, "")
                startActivityForResult(intent, ROUTE_SELECTION)
            }
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
}
