package com.zainco.wataniaroutes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment


class NewDialog(
    val hint: Int,
    val dialogtitle: Int,
    val oldValue: String = ""
) : DialogFragment() {
    private lateinit var textViewValue: EditText
    private lateinit var mCreate: TextView
    private lateinit var mCancel: TextView
    private lateinit var iCreateActivity: ICreateActivity
    private lateinit var iEditActivity: IEditActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = STYLE_NORMAL
        val theme = android.R.style.Theme_DeviceDefault_Light_Dialog
        setStyle(style, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_new_value, container, false)
        textViewValue = view.findViewById(R.id.textViewValue)
        textViewValue.setText(oldValue)
        textViewValue.hint = getString(hint)
        mCreate = view.findViewById(R.id.create)
        mCancel = view.findViewById(R.id.cancel)

        mCancel.setOnClickListener {
            dialog?.dismiss()
        }
        mCreate.setOnClickListener {
            val value = textViewValue.text.toString()
                if (oldValue != "") {//edit mode
                    iEditActivity.edit(value)
                } else {
                    iCreateActivity.createNew(value)
                }
                dialog?.dismiss()
        }

        dialog!!.setTitle(getString(dialogtitle))
        if (oldValue != "") {//edit mode
            mCreate.text = "تعديل"
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        iCreateActivity = activity as ICreateActivity
        iEditActivity = activity as IEditActivity
    }

}