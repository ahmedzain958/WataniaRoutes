package com.zainco.wataniaroutes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_project.*

class AddProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
        editRoute.setOnClickListener {

        }
        button.setOnClickListener {
            val project = Project(
                editAnnualRaise.text.toString().toDouble(),
                editArea.text.toString(),
                editEndDate.text.toString(),
                editInvestor.text.toString(),
                editLocation.text.toString(),
                editNotes.text.toString(),
                editPeriod.text.toString(),
                editPrice.text.toString().toInt(),
                editProject.text.toString(),
                spinnerRentType.selectedItemPosition,
                editRentValue.text.toString().toLong(),
                editRoute.text.toString(),
                editStartDate.text.toString()
            )


        }
    }
}
