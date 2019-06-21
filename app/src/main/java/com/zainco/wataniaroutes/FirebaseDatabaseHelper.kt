package com.zainco.wataniaroutes

import android.content.Context
import android.widget.Toast
import com.google.firebase.database.*


class FirebaseDatabaseHelper(
    val context: Context,
    private val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(),
    private val mReferenceProjects: DatabaseReference = mDatabase.getReference("Projects")
) {
    private val projects: MutableList<Project> = mutableListOf()

    interface DataStatus {
        fun dataIsLoaded(
            projects: List<Project>,
            keys: List<String>
        )

        fun dataIsInserted()
        fun dataIsUpdated()
        fun dataIsDeleted()
    }

    public fun readBooks(dataStatus: DataStatus) {
        mReferenceProjects.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                projects.clear()
                val keys = mutableListOf<String>()
                dataSnapshot.children.mapNotNullTo(projects) {
                    keys.add(it.key!!)
                    it.getValue<Project>(Project::class.java)
                }
                dataStatus.dataIsLoaded(projects, keys)
            }
        })
    }
}