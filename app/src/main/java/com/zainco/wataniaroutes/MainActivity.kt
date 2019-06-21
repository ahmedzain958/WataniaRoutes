package com.zainco.wataniaroutes

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    lateinit var adapter: ProjectsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.layoutManager = LinearLayoutManager(this)
        FirebaseDatabaseHelper(this).readBooks(object : FirebaseDatabaseHelper.DataStatus {
            override fun dataIsLoaded(projects: List<Project>, keys: List<String>) {
                progressBar.visibility = View.GONE
                adapter = ProjectsAdapter(projects)
                recycler.adapter = adapter
            }

            override fun dataIsInserted() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun dataIsUpdated() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun dataIsDeleted() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
