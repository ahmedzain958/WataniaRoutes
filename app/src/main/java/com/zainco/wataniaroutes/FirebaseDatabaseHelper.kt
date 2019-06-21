package com.zainco.wataniaroutes

import com.google.firebase.database.*


class FirebaseDatabaseHelper(
    private val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(),
    private val mReferenceProjects: DatabaseReference = mDatabase.getReference("Projects")
) {
    private val projects = mutableListOf<Project>()

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
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                projects.clear()
                val keys = mutableListOf<String>()
                dataSnapshot.children.forEach { keyNode ->
                    keys.add(keyNode.key!!)
                    val project = keyNode.value as Project
                    projects.add(project)
                }
                dataStatus.dataIsLoaded(projects, keys)
            }
        })
    }
}