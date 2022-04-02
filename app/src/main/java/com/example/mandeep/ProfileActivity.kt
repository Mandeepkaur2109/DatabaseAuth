package com.example.mandeep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.mandeep.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    val database = Firebase.database
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityProfileBinding
    lateinit var currentUser: FirebaseUser
    lateinit var myRef: DatabaseReference
    private  val TAG = "ProfileActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        currentUser = auth.currentUser!!
        myRef = database.getReference(currentUser!!.uid)

        binding.btnUpdateName.setOnClickListener {
            if(binding.etName.text.toString().isNullOrBlank()){
                Toast.makeText(this@ProfileActivity, "Enter name to update", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            myRef.setValue(binding.etName.text.toString())
        }
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name: String? = snapshot.getValue(String::class.java)
                name?.let {
                    binding.etName.setText(it)

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
            this.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.logout) {
            auth.signOut()
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
            this.finish()
            return true
        }


        return super.onOptionsItemSelected(item)

    }

}