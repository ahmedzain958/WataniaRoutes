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
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_add_route.*
import kotlinx.android.synthetic.main.uctd_toolbar.*


class RouteActivity : BaseActivity(), ICreateActivity ,IEditActivity {
    var selectedItem=""
    override fun edit(value: String) {
        val replacedValue = value.replace("/","-")
        val investorMap = mapOf("route" to replacedValue)
        routeRef
            .document(replacedValue).set(investorMap, SetOptions.merge())
            .addOnSuccessListener {
            }.addOnFailureListener {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
        val routeDocumentRef = routeRef.document(selectedItem)
        routeDocumentRef.delete().addOnCompleteListener {
            Toast.makeText(this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show()
        }
    }
    private val db = FirebaseFirestore.getInstance()
    private val routeRef: CollectionReference = db.collection("Routes")
    val routesNames = mutableListOf<String>()

    override fun createNew(value: String) {
        val replacedValue = value.replace("/","-")
        val routeMap = mapOf("route" to replacedValue)
        routeRef
            .document(replacedValue).set(routeMap)
            .addOnSuccessListener {
                Toast.makeText(this, "تم الإضافة بنجاح", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)
        initToolbar(uctdToolbar, getString(R.string.add_route), withBack = true)
        fab.setOnClickListener {
            NewDialog(R.string.route, R.string.add_route).show(
                getSupportFragmentManager(),
                getString(R.string.add_route)
            )
        }
        editTextSearchRoute.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val x = 0

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val x = 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadRoutes(editTextSearchRoute.text.toString())
            }

        })
    }

    fun loadRoutes(routeName: String) {
        routeRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recyclerViewRoutes.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    routesNames.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (!routeName.equals("")) {
                            if (documentSnapshot.id.contains(routeName)) {
                                routesNames.add(documentSnapshot.id)
                            }
                        } else {
                            routesNames.add(documentSnapshot.id)
                        }
                    }
                    fillRoutesList(routesNames)
                } else {
                    recyclerViewRoutes.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        loadRoutes("")
    }

    override fun onStart() {
        super.onStart()
        loadRoutes("")
    }

    private fun fillRoutesList(routesNames: MutableList<String>) {
        recyclerViewRoutes.layoutManager = LinearLayoutManager(this)
        recyclerViewRoutes.setHasFixedSize(true)
        recyclerViewRoutes.adapter = ValuesAdapter( routesNames, object : ValuesAdapter.OnRecycleClicked {
            override fun setOnRecycleClicked(value: String) {
                generateMessageAlert(
                    getString(R.string.delete_route) + " " + value,
                    getString(R.string.delete),
                    getString(R.string.cancel),
                    null,
                    object : DialogClickListener {
                        override fun onDialogButtonClick() {
                            val routeDocumentRef = routeRef.document(value)
                            routeDocumentRef.delete().addOnCompleteListener {
                                Toast.makeText(this@RouteActivity, "تم حذف الطريق", Toast.LENGTH_SHORT).show()
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
                        NewDialog(R.string.route,R.string.edit_route,value)
                    dialog.show(
                        supportFragmentManager,
                        getString(R.string.edit_route)
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
