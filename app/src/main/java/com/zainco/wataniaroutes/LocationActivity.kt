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
import kotlinx.android.synthetic.main.activity_add_location.*
import kotlinx.android.synthetic.main.uctd_toolbar.*


class LocationActivity : BaseActivity(), ICreateActivity {
    private val db = FirebaseFirestore.getInstance()
    private val locationRef: CollectionReference = db.collection("Locations")
    val locationsNames = mutableListOf<String>()

    override fun createNew(value: String) {
        val locationMap = mapOf("location" to value)
        locationRef
            .document(value).set(locationMap)
            .addOnSuccessListener {
                Toast.makeText(this, "تم الإضافة بنجاح", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
        initToolbar(uctdToolbar, getString(R.string.add_location), withBack = true)
        fab.setOnClickListener {
            NewDialog(R.string.location,R.string.add_location).show(getSupportFragmentManager(), getString(R.string.add_location))
        }
        editTextSearchLocation.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val x =0

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val x =0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadLocations (editTextSearchLocation.text.toString())
            }

        })
    }

    fun loadLocations(locationName: String) {
        locationRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recyclerViewLocations.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    locationsNames.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (!locationName.equals("")) {
                            if (documentSnapshot.id.contains(locationName)) {
                                locationsNames.add(documentSnapshot.id)
                            }
                        } else {
                            locationsNames.add(documentSnapshot.id)
                        }
                    }
                    fillLocationsList(locationsNames)
                } else {
                    recyclerViewLocations.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        loadLocations("")
    }

    override fun onStart() {
        super.onStart()
        loadLocations("")
    }

    private fun fillLocationsList(locationsNames: MutableList<String>) {
        recyclerViewLocations.layoutManager = LinearLayoutManager(this)
        recyclerViewLocations.setHasFixedSize(true)
        recyclerViewLocations.adapter = ValuesAdapter(R.string.location,locationsNames, object : ValuesAdapter.OnValueClicked {
            override fun setOnValueClicked(value: String) {
                generateMessageAlert(
                    getString(R.string.delete_location)+" " + value,
                    getString(R.string.delete),
                    getString(R.string.cancel),
                    null,
                    object : DialogClickListener {
                        override fun onDialogButtonClick() {
                            val locationDocumentRef = locationRef.document(value)
                            locationDocumentRef.delete().addOnCompleteListener {
                                Toast.makeText(this@LocationActivity, "تم حذف الموقع", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    null,
                    true
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
