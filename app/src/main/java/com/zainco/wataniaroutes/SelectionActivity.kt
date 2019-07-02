package com.zainco.wataniaroutes

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zainco.wataniaroutes.AddProjectActivity.Companion.INVESTOR_SELECTION_CODE
import com.zainco.wataniaroutes.AddProjectActivity.Companion.LOCATION_SELECTION_CODE
import com.zainco.wataniaroutes.AddProjectActivity.Companion.RENT_TYPE_SELECTION_CODE
import com.zainco.wataniaroutes.AddProjectActivity.Companion.ROUTE_SELECTION_CODE
import kotlinx.android.synthetic.main.activity_selection.*
import kotlinx.android.synthetic.main.uctd_toolbar.*

class SelectionActivity : BaseActivity() {
    companion object {
        const val SELECTION = "selection"
    }

    private val db = FirebaseFirestore.getInstance()
    private val routeRef: CollectionReference = db.collection("Routes")
    private val locationRef: CollectionReference = db.collection("Locations")
    private val rentRateRef: CollectionReference = db.collection("RentRates")
    private val investorRef: CollectionReference = db.collection("Investor")
    val list = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        getSelection()
    }

    private fun getSelection() {
        if (intent?.getIntExtra(SELECTION, -1) == ROUTE_SELECTION_CODE) {
            initToolbar(uctdToolbar, getString(R.string.choose_routes), withBack = true)
        } else if (intent?.getIntExtra(SELECTION, -1) == INVESTOR_SELECTION_CODE) {
            initToolbar(uctdToolbar, getString(R.string.choose_investors), withBack = true)
        } else if (intent?.getIntExtra(SELECTION, -1) == LOCATION_SELECTION_CODE) {
            initToolbar(uctdToolbar, getString(R.string.choose_location), withBack = true)
        } else if (intent?.getIntExtra(SELECTION, -1) == RENT_TYPE_SELECTION_CODE) {
            initToolbar(uctdToolbar, getString(R.string.choose_rent_type), withBack = true)
        }
        filter()
    }

    private fun filter() {
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val x = 0

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val x = 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (intent?.getIntExtra(SELECTION, -1) == ROUTE_SELECTION_CODE) {
                    loadRoutes(editTextSearch.text.toString())
                } else if (intent?.getIntExtra(SELECTION, -1) == INVESTOR_SELECTION_CODE) {
                    loadInvestors(editTextSearch.text.toString())
                } else if (intent?.getIntExtra(SELECTION, -1) == LOCATION_SELECTION_CODE) {
                    loadLocations(editTextSearch.text.toString())
                } else if (intent?.getIntExtra(SELECTION, -1) == RENT_TYPE_SELECTION_CODE) {
                    loadRentRate(editTextSearch.text.toString())
                }
            }

        })
    }

    override fun onStart() {
        super.onStart()
        if (intent?.getIntExtra(SELECTION, -1) == ROUTE_SELECTION_CODE) {
            loadRoutes("")
        } else if (intent?.getIntExtra(SELECTION, -1) == INVESTOR_SELECTION_CODE) {
            loadInvestors("")
        } else if (intent?.getIntExtra(SELECTION, -1) == LOCATION_SELECTION_CODE) {
            loadLocations("")
        } else if (intent?.getIntExtra(SELECTION, -1) == RENT_TYPE_SELECTION_CODE) {
            loadRentRate("")
        }
    }

    private fun loadRoutes(route: String) {
        routeRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recycle.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    list.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (!route.equals("")) {
                            if (documentSnapshot.id.contains(route)) {
                                list.add(documentSnapshot.id)
                            }
                        } else {
                            list.add(documentSnapshot.id)
                        }
                    }
                    fillList(list)
                } else {
                    recycle.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun loadRentRate(rentRate: String) {
        rentRateRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recycle.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    list.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (!rentRate.equals("")) {
                            if (documentSnapshot.id.contains(rentRate)) {
                                list.add(documentSnapshot.id)
                            }
                        } else {
                            list.add(documentSnapshot.id)
                        }
                    }
                    fillList(list)
                } else {
                    recycle.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun loadInvestors(investor: String) {
        investorRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recycle.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    list.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (!investor.equals("")) {
                            if (documentSnapshot.id.contains(investor)) {
                                list.add(documentSnapshot.id)
                            }
                        } else {
                            list.add(documentSnapshot.id)
                        }
                    }
                    fillList(list)
                } else {
                    recycle.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun loadLocations(locationName: String) {
        locationRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recycle.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    list.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (!locationName.equals("")) {
                            if (documentSnapshot.id.contains(locationName)) {
                                list.add(documentSnapshot.id)
                            }
                        } else {
                            list.add(documentSnapshot.id)
                        }
                    }
                    fillList(list)
                } else {
                    recycle.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }

            }

        }
    }

    private fun fillList(locationsNames: MutableList<String>) {
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.setHasFixedSize(true)
        recycle.adapter = SelectionAdapter(locationsNames, object : SelectionAdapter.OnValueClicked {
            override fun setOnValueClicked(value: String) {
                setResult(Activity.RESULT_OK, intent.putExtra(SELECTION, value))
                finish()
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
