package com.zainco.wataniaroutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_project.*

class AddProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
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

            FirebaseDatabaseHelper(this).addProject(project, object : FirebaseDatabaseHelper.DataStatus {
                override fun dataIsFiltered(query:String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun dataIsLoaded(projects: List<Project>, keys: List<String>) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun dataIsInserted() {
                    Toast.makeText(this@AddProjectActivity, "تم الحفظ بنجاح", Toast.LENGTH_LONG).show()
                }

                override fun dataIsUpdated() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun dataIsDeleted() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        }
    }
}
