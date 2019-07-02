package com.zainco.wataniaroutes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fab

class MainActivity : BaseActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val projectRef: CollectionReference = db.collection("projects")
    val projects = mutableListOf<Project>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        recycler.layoutManager = LinearLayoutManager(this)

    }

    override fun onResume() {
        super.onResume()
        loadProjects(null)
    }

    override fun onStart() {
        super.onStart()
        loadProjects(null)
    }

    fun loadProjects(project: Project?) {
        projectRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            } else {
                if (querySnapshot.size() > 0) {
                    recycler.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    projects.clear()
                    for (documentSnapshot in querySnapshot) {
                        if (project != null) {
                            if (documentSnapshot.id.contains(project.ProjectName!!)) {
                                val project = documentSnapshot.toObject(Project::class.java)
                                projects.add(project)
                            }
                        } else {
                            val project = documentSnapshot.toObject(Project::class.java)
                            projects.add(project)
                        }
                    }
                    fillProjectsList(projects)
                } else {
                    recycler.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                }

            }

        }
    }

    private fun fillProjectsList(projects: List<Project>) {
        recycler.setHasFixedSize(true)
        recycler.adapter = ProjectsAdapter(projects)
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
