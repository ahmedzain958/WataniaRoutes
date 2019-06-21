package com.zainco.wataniaroutes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_list_item.view.*

class ProjectsAdapter(val projects: List<Project>) : RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {
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
        val project: Project = projects.get(holder.adapterPosition)
        with(holder) {
            textViewProject.text = project.ProjectName
            textViewRoute.text = project.Route
            textViewInvestor.text = project.Investor
            textViewLocation.text = project.Location
            textViewArea.text = project.Area.toString()
            textViewRentValue.text = project.RentValue.toString()
        }
    }

    inner class ProjectsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewProject: TextView = view.textViewProject
        val textViewRoute: TextView = view.textViewRoute
        val textViewInvestor: TextView = view.textViewInvestor
        val textViewLocation: TextView = view.textViewLocation
        val textViewArea: TextView = view.textViewArea
        val textViewRentValue: TextView = view.textViewRentValue
    }
}