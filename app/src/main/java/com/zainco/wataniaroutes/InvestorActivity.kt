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
import kotlinx.android.synthetic.main.activity_add_investor.*
import kotlinx.android.synthetic.main.uctd_toolbar.*


class InvestorActivity : BaseActivity(), ICreateActivity, IEditActivity {
    var selectedItem=""
    override fun edit(value: String) {
        val replacedValue = value.replace("/", "-")
        val investorMap = mapOf("investor" to replacedValue)
        investorRef
            .document(replacedValue).set(investorMap)
            .addOnSuccessListener {
            }.addOnFailureListener {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
        val investorDocumentRef = investorRef.document(selectedItem)
        investorDocumentRef.delete().addOnCompleteListener {
            Toast.makeText(this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show()
        }
    }

    private val db = FirebaseFirestore.getInstance()
    private val investorRef: CollectionReference = db.collection("Investor")
    val investorsNames = mutableListOf<String>()

    override fun createNew(value: String) {
        val replacedValue = value.replace("/","-")
        val investorMap = mapOf("investor" to replacedValue)
        investorRef
            .document(replacedValue).set(investorMap)
            .addOnSuccessListener {
                Toast.makeText(this, "تم الإضافة بنجاح", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_investor)
        initToolbar(uctdToolbar, getString(R.string.add_investor), withBack = true)
        fab.setOnClickListener {
            NewDialog(R.string.investor,R.string.add_investor).show(getSupportFragmentManager(), getString(R.string.add_investor))
        }
        editTextSearchInvestor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val x =0

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val x =0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadInvestors (editTextSearchInvestor.text.toString())
            }

        })
    }

    fun loadInvestors(InvestorName: String) {
        investorRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recyclerViewInvestors.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    investorsNames.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (!InvestorName.equals("")) {
                            if (documentSnapshot.id.contains(InvestorName)) {
                                investorsNames.add(documentSnapshot.id)
                            }
                        } else {
                            investorsNames.add(documentSnapshot.id)
                        }
                    }
                    fillInvestorsList(investorsNames)
                } else {
                    recyclerViewInvestors.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        loadInvestors("")
    }

    override fun onStart() {
        super.onStart()
        loadInvestors("")
    }

    private fun fillInvestorsList(InvestorsNames: MutableList<String>) {
        recyclerViewInvestors.layoutManager = LinearLayoutManager(this)
        recyclerViewInvestors.setHasFixedSize(true)
        recyclerViewInvestors.adapter = ValuesAdapter(InvestorsNames, object : ValuesAdapter.OnRecycleClicked {
            override fun setOnRecycleClicked(value: String) {
                generateMessageAlert(
                    getString(R.string.delete_investor)+" " + value,
                    getString(R.string.delete),
                    getString(R.string.cancel),
                    null,
                    object : DialogClickListener {
                        override fun onDialogButtonClick() {
                            val InvestorDocumentRef = investorRef.document(value)
                            InvestorDocumentRef.delete().addOnCompleteListener {
                                Toast.makeText(this@InvestorActivity, "تم حذف المستثمر", Toast.LENGTH_SHORT).show()
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
                    investorRef.document(value)
                        .get().addOnSuccessListener {
                            val dialog =
                                NewDialog(R.string.investor, R.string.edit_investor, value)
                            dialog.show(
                                supportFragmentManager,
                                getString(R.string.edit_investor)
                            )
                        }
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
