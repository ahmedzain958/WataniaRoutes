package com.zainco.wataniaroutes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_rentrate.*
import kotlinx.android.synthetic.main.uctd_toolbar.*


class RentRateActivity : BaseActivity(), ICreateActivity ,IEditActivity {
    var selectedItem=""
    override fun edit(value: String) {
        val replacedValue = value.replace("/","-")
        val investorMap = mapOf("rentrate" to replacedValue)
        rentRateRef
            .document(replacedValue).set(investorMap)
            .addOnSuccessListener {
            }.addOnFailureListener {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
        val rentRateDocumentRef = rentRateRef.document(selectedItem)
        rentRateDocumentRef.delete().addOnCompleteListener {
            Toast.makeText(this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show()
        }
    }
    private val db = FirebaseFirestore.getInstance()
    private val rentRateRef: CollectionReference = db.collection("RentRates")
    val rentRatesNames = mutableListOf<String>()

    override fun createNew(value: String) {
        val replacedValue = value.replace("/","-")
        val rentRatesMap = mapOf("rentrate" to replacedValue)
        rentRateRef
            .document(replacedValue).set(rentRatesMap)
            .addOnSuccessListener {
                Toast.makeText(this, "تم الإضافة بنجاح", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rentrate)
        initToolbar(uctdToolbar, getString(R.string.add_rentrate), withBack = true)
        fab.setOnClickListener {
            NewDialog(R.string.rentrate,R.string.add_rentrate,"").show(supportFragmentManager, getString(R.string.add_rentrate))
        }
        editTextSearchRentRate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val x =0

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val x =0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadRentRates(editTextSearchRentRate.text.toString())
            }

        })
    }

    fun loadRentRates(rentRateName: String) {
        rentRateRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recyclerViewRentRates.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    rentRatesNames.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (!rentRateName.equals("")) {
                            if (documentSnapshot.id.contains(rentRateName)) {
                                rentRatesNames.add(documentSnapshot.id)
                            }
                        } else {
                            rentRatesNames.add(documentSnapshot.id)
                        }
                    }
                    fillRentratesList(rentRatesNames)
                } else {
                    recyclerViewRentRates.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        loadRentRates("")
    }

    override fun onStart() {
        super.onStart()
        loadRentRates("")
    }

    private fun fillRentratesList(rentRatesNames: MutableList<String>) {
        recyclerViewRentRates.layoutManager = LinearLayoutManager(this)
        recyclerViewRentRates.setHasFixedSize(true)
        recyclerViewRentRates.adapter = ValuesAdapter(rentRatesNames, object : ValuesAdapter.OnRecycleClicked {
            override fun setOnRecycleClicked(value: String) {
                generateMessageAlert(
                    getString(R.string.delete_rentrate)+" " + value,
                    getString(R.string.delete),
                    getString(R.string.cancel),
                    null,
                    object : DialogClickListener {
                        override fun onDialogButtonClick() {
                            val rentRateDocumentRef = rentRateRef.document(value)
                            rentRateDocumentRef.delete().addOnCompleteListener {
                                Toast.makeText(this@RentRateActivity, "تم حذف معدل الايجار", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    null,
                    true
                )
            }
        },
            object : ValuesAdapter.OnItemClicked {
                override fun setOnItemClicked(value: String) {
                    selectedItem = value
                    val dialog =
                        NewDialog(R.string.rentrate,R.string.edit_rent_rate,value)
                    dialog.show(
                        supportFragmentManager,
                        getString(R.string.edit_rent_rate)
                    )
                }

            })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
