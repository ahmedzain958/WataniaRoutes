package com.zainco.wataniaroutes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.values_list_item.view.*

class ValuesAdapter(
    val titleId: Int,
    internal var values: List<String>, val onValueClicked: OnValueClicked
) : RecyclerView.Adapter<ValuesAdapter.ValuesHolder>() {
    lateinit var context: Context

    interface OnValueClicked {
        fun setOnValueClicked(value: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuesHolder {
        context = parent.context
        return ValuesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.values_list_item,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ValuesHolder, position: Int) {
        val value: String = values.get(holder.adapterPosition)
        with(holder) {
            textViewValue.text = value
            textViewTitle.text = context.getString(titleId)
            this.itemView.setOnClickListener {
                onValueClicked.setOnValueClicked(value)
            }
        }
    }

    inner class ValuesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewValue: TextView = view.textViewValue
        val textViewTitle: TextView = view.textViewTitle
    }
}