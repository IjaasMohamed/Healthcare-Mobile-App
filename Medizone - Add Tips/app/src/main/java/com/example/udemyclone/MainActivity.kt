package com.example.udemyclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.udemyclone.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), TipsRVAdapter.CourseClickInterface{
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var courseRVModalArrayList: ArrayList<TipsRVModel>
    private lateinit var courseRVAdapter: TipsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //! binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //! Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Tips")

        //! Add Course Button
        binding.btnAddCourse.setOnClickListener {
            startActivity(Intent(this, AddTipsActivity::class.java))
        }

        //! courseRV Array List.
        courseRVModalArrayList = ArrayList()

        //! recyclerView
        binding.rvCourses.layoutManager = LinearLayoutManager(this)
        courseRVAdapter = TipsRVAdapter(courseRVModalArrayList, this, this)
        binding.rvCourses.adapter = courseRVAdapter
        getAllCourses()
    }

    private fun getAllCourses() {
        courseRVModalArrayList.clear()
        databaseReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //courseRVModalArrayList.add(snapshot.getValue(CourseRVModal::class.java))
                snapshot.getValue(TipsRVModel::class.java)?.let { courseRVModalArrayList.add(it) }
                courseRVAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                courseRVAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                courseRVAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                courseRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    //! Menu creation
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    //! on menu selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.mLogout) {
            Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show()
            mAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCourseClick(position: Int) {
        val intent = Intent(this, TipsDetails::class.java)
        intent.putExtra("TipsID", courseRVModalArrayList[position].tipsName)
        startActivity(intent)
        finish()
    }
}