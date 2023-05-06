package com.example.realtimedatabasekotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabasekotlin.databinding.ActivityViewDatasBinding
import com.google.firebase.database.*
import java.util.*

class ViewDatas : AppCompatActivity() {
    private lateinit var binding: ActivityViewDatasBinding

    private lateinit var dbref: DatabaseReference
    private lateinit var empRecyclerview: RecyclerView
    private lateinit var empList: ArrayList<doctor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewDatasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, AddDetails::class.java)
            startActivity(intent)

        }

        binding.viewBtn.setOnClickListener {
            val intent = Intent(this, ViewDatas::class.java)
            startActivity(intent)
            finish()
        }

        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)

        }

        binding.logoutBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        empRecyclerview = findViewById(R.id.doctorRecycleView)
        empRecyclerview.layoutManager = LinearLayoutManager(this)
        empRecyclerview.setHasFixedSize(true)

        empList = arrayListOf<doctor>()
        getUserData()
    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Doctors")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {

                        val doctor = userSnapshot.getValue(doctor::class.java)
                        empList.add(doctor!!)

                    }
                    val mAdapter = doctorAdaptor(empList,this@ViewDatas)
                    empRecyclerview.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : doctorAdaptor.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@ViewDatas, editDatas::class.java)

                            //put extras
                            intent.putExtra("name", empList[position].name)
                            intent.putExtra("id", empList[position].id)
                            intent.putExtra("number", empList[position].number)
                            intent.putExtra("special", empList[position].special)
                            intent.putExtra("date", empList[position].date)
                            intent.putExtra("imageUri", empList[position].image)

                            startActivity(intent)
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

