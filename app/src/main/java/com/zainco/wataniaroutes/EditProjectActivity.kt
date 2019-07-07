package com.zainco.wataniaroutes

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zainco.wataniaroutes.SelectionActivity.Companion.SELECTION
import kotlinx.android.synthetic.main.activity_edit_project.*
import java.text.SimpleDateFormat
import java.util.*

class EditProjectActivity : AppCompatActivity() {
    companion object {
        const val ROUTE_SELECTION_CODE = 300
        const val INVESTOR_SELECTION_CODE = 301
        const val LOCATION_SELECTION_CODE = 302
        const val RENT_TYPE_SELECTION_CODE = 303
    }

    private val db = FirebaseFirestore.getInstance()
    lateinit var project: Project
    private val projectRef: CollectionReference = db.collection("projects")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_project)
        project = intent.extras.getSerializable("project") as Project
        loadViews()
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
        editArea.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val x = 0

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val x = 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onAreaOrPriceChange()
            }

        })
        editPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val x = 0

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val x = 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onAreaOrPriceChange()
            }

        })

        button.setOnClickListener {
            if (editProject.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال اسم المشروع", Toast.LENGTH_SHORT).show()
            } else if (textRoute.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال الطريق", Toast.LENGTH_SHORT).show()
            } else if (textInvestor.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال اسم المستثمر", Toast.LENGTH_SHORT).show()
            } else if (textRentType.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "يرجي ادخال معدل ايجار", Toast.LENGTH_SHORT).show()
            } else {
                val annualRaise: Double?
                val area: Double?
                val endDate: String
                val notes: String
                val period: String
                val price: Int
                val startDate: String

                if (editAnnualRaise.text.toString().trim().isEmpty())
                    annualRaise = 0.0
                else
                    annualRaise = editAnnualRaise.text.toString().toDouble()

                if (editArea.text.toString().trim().isEmpty())
                    area = 0.0
                else
                    area = editArea.text.toString().toDouble()

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
                    area * price,
                    textRoute.text.toString(),
                    startDate
                )
                var updated = false
                val replacedValue = editProject.text.toString().replace("/","-")
                db.collection("projects")
                    .document(replacedValue)
                    .set(project)
                    .addOnSuccessListener {
                        if (editProject.text.toString().trim() != project.ProjectName) {
                            updated = true
                        }else{
                            updated = false
                            Toast.makeText(this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
                    }
                if (updated) {
                    val projectDocumentRef = projectRef.document(project.ProjectName)
                    projectDocumentRef.delete().addOnCompleteListener {
                        Toast.makeText(this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    fun onAreaOrPriceChange() {
        val area = if (editArea.text.toString().isEmpty()) 0.0 else editArea.text.toString().toDouble()
        val price = if (editPrice.text.toString().isEmpty()) 0.0 else editPrice.text.toString().toDouble()
        val rentValue = area * price
        textRentValue.text = rentValue.toString()
    }
    private fun loadViews() {
        editAnnualRaise.setText(project.AnnualRaise.toString())
        editArea.setText(project.Area.toString())
        textEndDate.setText(project.EndDate.toString())
        textStartDate.setText(project.StartDate.toString())
        editNotes.setText(project.Notes.toString())
        editPeriod.setText(project.Period.toString())
        editPrice.setText(project.Price.toString())
        textRentValue.setText(project.RentValue.toString())
        textRoute.setText(project.Route)
        textInvestor.setText(project.Investor)
        textLocation.setText(project.Location)
        textRentType.setText(project.RentRate)
        editProject.setText(project.ProjectName)
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
