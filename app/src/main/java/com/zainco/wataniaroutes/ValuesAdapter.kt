package com.zainco.wataniaroutes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.values_list_item.view.*

class ValuesAdapter(
    private var values: List<String>,
    private val onRecycleClicked: OnRecycleClicked,
    val onItemClicked: OnItemClicked
) : RecyclerView.Adapter<ValuesAdapter.ValuesHolder>() {
    lateinit var context: Context

    interface OnRecycleClicked {
        fun setOnRecycleClicked(value: String)
    }

    interface OnItemClicked {
        fun setOnItemClicked(value: String)
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
            this.delete.setOnClickListener {
                onRecycleClicked.setOnRecycleClicked(value)
            }
            this.itemView.setOnClickListener {
                onItemClicked.setOnItemClicked(value)
            }
        }
    }

    inner class ValuesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewValue: TextView = view.textViewValue
        val delete: ImageView = view.delete
    }
}