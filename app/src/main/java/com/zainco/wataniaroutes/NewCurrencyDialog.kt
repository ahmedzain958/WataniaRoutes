package com.zainco.wataniaroutes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment


class NewCurrencyDialog(
    val hint: Int,
    val hintCurrency: Int,
    val dialogtitle: Int,
    val currencyValue: String = "",
    val egyptianValue: String = ""
) : DialogFragment() {
    private lateinit var textViewValue: EditText
    private lateinit var textViewEgyptianValue: EditText
    private lateinit var mCreate: TextView
    private lateinit var mCancel: TextView
    private lateinit var iCreateActivity: ICreateCurrencyActivity
    private lateinit var ieditCurrencyActivity: EditCurrencyActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = STYLE_NORMAL
        val theme = android.R.style.Theme_DeviceDefault_Light_Dialog
        setStyle(style, theme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_new_currency, container, false)
        textViewValue = view.findViewById(R.id.textViewValue)
        textViewValue.setText(currencyValue)
        textViewEgyptianValue = view.findViewById(R.id.textViewEgyptianValue)
        textViewValue.hint = getString(hint)
        textViewEgyptianValue.hint = getString(hintCurrency)
        textViewEgyptianValue.setText(egyptianValue)
        mCreate = view.findViewById(R.id.create)
        mCancel = view.findViewById(R.id.cancel)

        mCancel.setOnClickListener {
            dialog?.dismiss()
        }
        mCreate.setOnClickListener {
            val value = textViewValue.text.toString()
            val egyptianValue = textViewEgyptianValue.text.toString()
            if (value != "" && egyptianValue != "" && egyptianValue != "0") {
                if (currencyValue != "") {//edit mode
                    ieditCurrencyActivity.editCurrency(value, egyptianValue)
                } else {
                    iCreateActivity.createNew(value, egyptianValue)
                }
                dialog?.dismiss()
            } else {
                textViewValue.error = "خطأ"
            }
        }

        dialog!!.setTitle(getString(dialogtitle))
        if (currencyValue != "") {//edit mode
            mCreate.text = "تعديل"
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        iCreateActivity = activity as ICreateCurrencyActivity
        ieditCurrencyActivity = activity as EditCurrencyActivity
    }

}