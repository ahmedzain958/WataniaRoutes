package com.zainco.wataniaroutes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_list_item.view.*

class ProjectsAdapter(
    val projects: List<Project>,
    val longClickListener: LongClickListener,
    val clickListener: ClickListener
) :
    RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {


    interface LongClickListener {
        fun onLongClick(project: Project)
    }

    interface ClickListener {
        fun onClick(project: Project)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        return ProjectsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.project_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = projects.size
    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        val project: Project = projects[holder.adapterPosition]
        with(holder) {
            textViewProject.text = project.ProjectName
            textViewRoute.text = project.Route
            textViewInvestor.text = project.Investor
            textViewLocation.text = project.Location
            textViewArea.text = project.Area.toString()
            textViewRentValue.text = project.RentValue.toString()
        }
    }

    inner class ProjectsViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener,
        View.OnClickListener {
        val textViewProject: TextView = view.textViewProject
        val textViewRoute: TextView = view.textViewValue
        val textViewInvestor: TextView = view.textViewInvestor
        val textViewLocation: TextView = view.textViewLocation
        val textViewArea: TextView = view.textViewArea
        val textViewRentValue: TextView = view.textViewRentValue
        override fun onLongClick(p0: View?): Boolean {
            longClickListener.onLongClick(projects[adapterPosition])
            return true
        }
        override fun onClick(p0: View?) {
            clickListener.onClick(projects[adapterPosition])

        }
        init {
            view.setOnLongClickListener(this)
            view.setOnClickListener(this)
        }
    }
}