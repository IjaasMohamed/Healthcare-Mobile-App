package com.example.realtimedatabasekotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabasekotlin.databinding.ActivityUserBinding
import com.google.firebase.database.*
import java.util.*

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var database: DatabaseReference
    private lateinit var empRecyclerview: RecyclerView
    private lateinit var empList: ArrayList<doctor>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.logoutBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        empRecyclerview = findViewById(R.id.doctorRecycleView2)
        empRecyclerview.layoutManager = LinearLayoutManager(this)
        empRecyclerview.setHasFixedSize(true)

        empList = arrayListOf<doctor>()
        getUserData()

    }

    private fun getUserData() {

        database = FirebaseDatabase.getInstance().getReference("Doctors")

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {

                        val doctor = userSnapshot.getValue(doctor::class.java)
                        empList.add(doctor!!)

                    }
                    val mAdapter = doctorAdaptor(empList,this@UserActivity)
                    empRecyclerview.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : doctorAdaptor.onItemClickListener {
                        override fun onItemClick(position: Int) {
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}