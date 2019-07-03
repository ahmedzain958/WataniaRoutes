package com.zainco.wataniaroutes

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.zainco.wataniaroutes.SelectionActivity.Companion.SELECTION
import kotlinx.android.synthetic.main.activity_add_project.*
import java.text.SimpleDateFormat
import java.util.*

class AddProjectActivity : AppCompatActivity() {
    companion object {
        const val ROUTE_SELECTION_CODE = 300
        const val INVESTOR_SELECTION_CODE = 301
        const val LOCATION_SELECTION_CODE = 302
        const val RENT_TYPE_SELECTION_CODE = 303
    }

    private val db = FirebaseFirestore.getInstance()
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
        textStartDate.setOnClickListener {
            val dateFromListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedCalender = Calendar.getInstance()
                selectedCalender.set(Calendar.YEAR, year)
                selectedCalender.set(Calendar.MONTH, monthOfYear)
                selectedCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val todaysDate = SimpleDateFormat("yyyy MM dd").format(selectedCalender.time)
                textStartDate.text = todaysDate
            }
            val datePickerDialog =
                DatePickerDialog(
                    this, dateFromListener,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )
            datePickerDialog.show()
        }
        textEndDate.setOnClickListener {
            val dateToListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedCalender = Calendar.getInstance()
                selectedCalender.set(Calendar.YEAR, year)
                selectedCalender.set(Calendar.MONTH, monthOfYear)
                selectedCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val todaysDate = SimpleDateFormat("yyyy MM dd").format(selectedCalender.time)
                textEndDate.text = todaysDate
            }
            val datePickerDialog =
                DatePickerDialog(
                    this, dateToListener,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )
            datePickerDialog.show()
        }

        button.setOnClickListener {
            if (editProject.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال اسم المشروع", Toast.LENGTH_SHORT).show()
            } else if (textRoute.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال الطريق", Toast.LENGTH_SHORT).show()
            } else if (textInvestor.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال اسم المستثمر", Toast.LENGTH_SHORT).show()
            } else if (textLocation.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال الموقع", Toast.LENGTH_SHORT).show()
            } else if (textRentType.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال معدل ايجار", Toast.LENGTH_SHORT).show()
            } else if (textRentType.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال معدل ايجار", Toast.LENGTH_SHORT).show()
            } else if (textRentType.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال معدل ايجار", Toast.LENGTH_SHORT).show()
            } else if (textRentType.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال معدل ايجار", Toast.LENGTH_SHORT).show()
            } else {
                val annualRaise: Double?
                val area: String?
                val endDate: String
                val notes: String
                val period: String
                val price: Int
                val rentValue: Long
                val startDate: String

                if (editAnnualRaise.text.toString().trim().isEmpty())
                    annualRaise = 0.0
                else
                    annualRaise = editAnnualRaise.text.toString().toDouble()

                if (editArea.text.toString().trim().isEmpty())
                    area = ""
                else
                    area = editArea.text.toString()

                if (textEndDate.text.toString().trim().isEmpty())
                    endDate = ""
                else
                    endDate = textEndDate.text.toString()

                if (textStartDate.text.toString().trim().isEmpty())
                    startDate = ""
                else
                    startDate = textEndDate.text.toString()

                if (editNotes.text.toString().trim().isEmpty())
                    notes = ""
                else
                    notes = editNotes.text.toString()

                if (editPeriod.text.toString().trim().isEmpty())
                    period = ""
                else
                    period = editPeriod.text.toString()

                if (editPrice.text.toString().trim().isEmpty())
                    price = 0
                else
                    price = editPrice.text.toString().toInt()

                if (editRentValue.text.toString().trim().isEmpty())
                    rentValue = 0
                else
                    rentValue = editRentValue.text.toString().toLong()


                val project = Project(
                    annualRaise,
                    area,
                    endDate,
                    textInvestor.text.toString(),
                    textLocation.text.toString(),
                    notes,
                    period,
                    price,
                    editProject.text.toString(),
                    textRentType.text.toString(),
                    rentValue,
                    textRoute.text.toString(),
                    startDate
                )
                val replacedValue = editProject.text.toString().replace("/","-")
                db.collection("projects")
                    .document(replacedValue)
                    .set(project)
                    .addOnSuccessListener {
                        Toast.makeText(this, "تم الإضافة بنجاح", Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
                    }

            }
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
