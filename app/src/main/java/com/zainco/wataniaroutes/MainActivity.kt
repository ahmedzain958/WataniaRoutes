package com.zainco.wataniaroutes

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

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
        editTextSearchProject.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val x = 0

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val x = 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadProjects(Project(ProjectName = s.toString().trim()))
            }

        })
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
                    progressBar.visibility = View.GONE
                    projects.clear()
                    for (documentSnapshot in querySnapshot) {
                        val firestoreProject = documentSnapshot.toObject(Project::class.java)
                        if (project != null) {
                            if (documentSnapshot.id.contains(project.ProjectName)) {
                                projects.add(firestoreProject)
                            }
                        } else {
                            projects.add(firestoreProject)
                        }
                    }
                    fillProjectsList(projects)
                } else {
                    recycler.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }

            }

        }
    }

    private fun fillProjectsList(projects: List<Project>) {
        recycler.setHasFixedSize(true)
        recycler.adapter = ProjectsAdapter(projects, object : ProjectsAdapter.LongClickListener {
            override fun onLongClick(project: Project) {
                generateMessageAlert(
                    getString(R.string.delete_project) + " " + project.ProjectName,
                    getString(R.string.delete),
                    getString(R.string.cancel),
                    null,
                    object : DialogClickListener {
                        override fun onDialogButtonClick() {
                            val projectDocumentRef = projectRef.document(project.ProjectName)
                            projectDocumentRef.delete().addOnCompleteListener {
                                Toast.makeText(this@MainActivity, "تم حذف المشروع", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    null,
                    true
                )
            }
        }, object : ProjectsAdapter.ClickListener {
            override fun onClick(project: Project) {
                val intent = Intent(this@MainActivity, EditProjectActivity::class.java)
                intent.putExtra("project", project)
                startActivity(intent)
            }

        })
        if (projects.size == 0) {
            recycler.visibility = View.GONE
            textNoData.visibility = View.VISIBLE
        }
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
