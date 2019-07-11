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
import kotlinx.android.synthetic.main.activity_add_currency.*
import kotlinx.android.synthetic.main.uctd_toolbar.*


class CurrencyActivity : BaseActivity(), ICreateCurrencyActivity {
    private val db = FirebaseFirestore.getInstance()
    private val currencyRef: CollectionReference = db.collection("currency")
    val currencyNames = mutableListOf<String>()

    override fun createNew(value: String, egyptianValue: String) {
        val replacedValue = value.replace("/", "-")
        val currencyMap = mapOf(
            "currency" to replacedValue,
            "egyptianValue" to egyptianValue
        )
        currencyRef
            .document(replacedValue).set(currencyMap)
            .addOnSuccessListener {
                Toast.makeText(this, "تم الإضافة بنجاح", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_currency)
        initToolbar(uctdToolbar, getString(R.string.add_currency), withBack = true)
        fab.setOnClickListener {
            NewCurrencyDialog(R.string.currency, R.string.egyptian_value, R.string.add_currency).show(
                supportFragmentManager,
                getString(R.string.add_currency)
            )
        }
        editTextSearchCurrency.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val x = 0

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val x = 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadCurrencies(editTextSearchCurrency.text.toString())
            }

        })
    }

    fun loadCurrencies(currency: String) {
        currencyRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recyclerViewCurrency.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    currencyNames.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (currency != "") {
                            if (documentSnapshot.id.contains(currency)) {
                                currencyNames.add(documentSnapshot.id)
                            }
                        } else {
                            currencyNames.add(documentSnapshot.id)
                        }
                    }
                    fillCurrenciesList(currencyNames)
                } else {
                    recyclerViewCurrency.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        loadCurrencies("")
    }

    override fun onStart() {
        super.onStart()
        loadCurrencies("")
    }

    private fun fillCurrenciesList(currencies: MutableList<String>) {
        recyclerViewCurrency.layoutManager = LinearLayoutManager(this)
        recyclerViewCurrency.setHasFixedSize(true)
        recyclerViewCurrency.adapter =
            ValuesAdapter( currencies, object : ValuesAdapter.OnRecycleClicked {
                override fun setOnRecycleClicked(value: String) {
                    generateMessageAlert(
                        getString(R.string.delete_currency) + " " + value,
                        getString(R.string.delete),
                        getString(R.string.cancel),
                        null,
                        object : DialogClickListener {
                            override fun onDialogButtonClick() {
                                val currencyDocumentRef = currencyRef.document(value)
                                currencyDocumentRef.delete().addOnCompleteListener {
                                    Toast.makeText(this@CurrencyActivity, "تم حذف العملة", Toast.LENGTH_SHORT)
                                        .show()
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
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
