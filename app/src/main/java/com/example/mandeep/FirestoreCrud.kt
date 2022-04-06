package com.example.mandeep

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.mandeep.databinding.ActivityFirestoreCrudBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreCrud : AppCompatActivity() {
    lateinit var binding:ActivityFirestoreCrudBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_firestore_crud)
        val db = Firebase.firestore
        val user = hashMapOf(
            "first" to "Mandeep",
            "last" to "lastname",
            "class" to "Btech",
            "Rollno" to "1803594"

        )
        binding.btnAdd.setOnClickListener {
            db.collection("documents").document("details of student")
                .set(user)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
        binding.btnUpdate.setOnClickListener {
            db.collection("documents").document("details of student")
                .update(
                    mapOf(
                        "first" to "tarandeep",
                        "last" to "kaur"
                    )
                )
        }
        binding.btnDelete.setOnClickListener {
            db.collection("documents").document("details of student").delete()
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
        }
        // Deleting collections from an Android client is not recommended.




    }
}