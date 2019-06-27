package com.zainco.wataniaroutes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    lateinit var adapter: ProjectsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {

        }
        recycler.layoutManager = LinearLayoutManager(this)
        FirebaseDatabaseHelper(this).readBooks(object : FirebaseDatabaseHelper.DataStatus {
            override fun dataIsFiltered(query: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

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


        FirebaseDatabaseHelper(this).searchProjects(Project(10.0), object : FirebaseDatabaseHelper.DataStatus {
            override fun dataIsFiltered(query: String) {
                Log.d("queryInserted", query)
            }

            override fun dataIsLoaded(projects: List<Project>, keys: List<String>) {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.action_add) {
            val intent = Intent(this, AddProjectActivity::class.java)
            startActivity(intent)
            return true
        } else if (id == R.id.action_search) {
            return false
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
}
