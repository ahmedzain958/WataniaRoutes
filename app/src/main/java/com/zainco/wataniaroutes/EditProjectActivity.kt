package com.zainco.wataniaroutes

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.zainco.wataniaroutes.SelectionActivity.Companion.SELECTION
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.activity_edit_project.button
import kotlinx.android.synthetic.main.activity_edit_project.editAnnualRaise
import kotlinx.android.synthetic.main.activity_edit_project.editArea
import kotlinx.android.synthetic.main.activity_edit_project.editNotes
import kotlinx.android.synthetic.main.activity_edit_project.editPeriod
import kotlinx.android.synthetic.main.activity_edit_project.editPrice
import kotlinx.android.synthetic.main.activity_edit_project.editProject
import kotlinx.android.synthetic.main.activity_edit_project.textEndDate
import kotlinx.android.synthetic.main.activity_edit_project.textInvestor
import kotlinx.android.synthetic.main.activity_edit_project.textLocation
import kotlinx.android.synthetic.main.activity_edit_project.textRentType
import kotlinx.android.synthetic.main.activity_edit_project.textRentValue
import kotlinx.android.synthetic.main.activity_edit_project.textRoute
import kotlinx.android.synthetic.main.activity_edit_project.textStartDate
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
    private val currencyRef: CollectionReference = db.collection("currency")
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
                onAreaOrPriceChange(if (!editPrice.text.toString().trim().isEmpty()) editPrice.text.toString().toDouble() else 0.0)
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
                onAreaOrPriceChange(if (!s.toString().trim().isEmpty()) s.toString().toDouble() else 0.0)
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
                var price: Double = 0.0
                val startDate: String
                var project: Project

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

                if (editPrice.text.toString().trim().isEmpty()) {
                    price = 0.0
                } else {
                    var foreign = 1.0
                    val currency = currency.text.toString()
                    if (currency == "جنيه") {
                        price = foreign * editPrice.text.toString().toDouble()
                        val rentValue = area * price
                        textRentValue.text = String.format("%.2f", rentValue)
                        project = Project(
                            annualRaise,
                            area,
                            endDate,
                            textInvestor.text.toString(),
                            textLocation.text.toString(),
                            notes,
                            period,
                            price/foreign,
                            editProject.text.toString(),
                            textRentType.text.toString(),
                            area * price,
                            textRoute.text.toString(),
                            startDate,
                            currency
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
                    } else {
                        currencyRef.document(currency).get()
                            .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                                if (documentSnapshot.exists()) {
                                    foreign = documentSnapshot.getString("egyptianValue")!!.toDouble()
                                    price = foreign * editPrice.text.toString().toDouble()
                                    val rentValue = area * price
                                    textRentValue.text = String.format("%.2f", rentValue)
                                    project = Project(
                                        annualRaise,
                                        area,
                                        endDate,
                                        textInvestor.text.toString(),
                                        textLocation.text.toString(),
                                        notes,
                                        period,
                                        price/foreign,
                                        editProject.text.toString(),
                                        textRentType.text.toString(),
                                        area * price,
                                        textRoute.text.toString(),
                                        startDate,
                                        currency
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
                                } else {
                                    Toast.makeText(this, "يرجي تعديل العملة", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }.addOnFailureListener {
                                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
                            }
                    }
                }


            }
        }
        btnCurrency.setOnClickListener {
            showCurrenciesDialog()
        }
    }

    private fun showCurrenciesDialog() {
        val builder = AlertDialog.Builder(this@EditProjectActivity)
        builder.setTitle("اختر العملة")
        currencyRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                val currencyNames = arrayListOf<String>()
                if (querySnapshot.size() > 0) {
                    currencyNames.clear()
                    querySnapshot.map {
                        currencyNames.add(it.id)
                    }
                    val currencies = currencyNames.toTypedArray()
                    builder.setSingleChoiceItems(
                        currencies,
                        -1
                    ) { dialogInterface, p1 ->
                        currency.text = currencies[p1]
                        dialogInterface.dismiss()
                        onAreaOrPriceChange(if (!editPrice.text.toString().trim().isEmpty()) editPrice.text.toString().toDouble() else 0.0)
                    }

                    val dialog = builder.create()
                    dialog.show()
                } else {
                    Toast.makeText(this, "لا يوجد عملات", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun onAreaOrPriceChange(priceValue: Double = 0.0) {
        val area = if (editArea.text.toString().isEmpty()) 0.0 else editArea.text.toString().toDouble()
        val currency = currency.text.toString()
        var foreign = 1.0
        var price = 0.0
        if (editPrice.text.toString().isEmpty()) {
            price = 0.0
        } else {
            if (currency == "جنيه") {
                price = foreign * priceValue
                val rentValue = area * price
                textRentValue.text = String.format("%.2f", rentValue)
            } else {
                currencyRef.document(currency).get().addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                    if (documentSnapshot.exists()) {
                        foreign = documentSnapshot.getString("egyptianValue")!!.toDouble()
                        price = foreign * priceValue
                        val rentValue = area * price
                        textRentValue.text = String.format("%.2f", rentValue)
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadViews() {
        editAnnualRaise.setText(project.AnnualRaise.toString())
        editArea.setText(project.Area.toString())
        textEndDate.setText(project.EndDate.toString())
        textStartDate.setText(project.StartDate.toString())
        editNotes.setText(project.Notes.toString())
        editPeriod.setText(project.Period.toString())
        editPrice.run { setText(project.Price.toString()) }
        textRentValue.text = project.RentValue.toString()
        textRoute.text = project.Route
        textInvestor.text = project.Investor
        textLocation.text = project.Location
        textRentType.text = project.RentRate
        editProject.setText(project.ProjectName)
        currency.text = project.currency
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
