package com.example.udemyclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.udemyclone.databinding.ActivityAddTipsBinding
import com.google.firebase.database.*

class AddTipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTipsBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tips)
        binding = ActivityAddTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Add Tips"

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Tips")
        
        binding.btnAddCourse.setOnClickListener {
            val courseName = binding.etCourseName.text.toString()
            val courseSuitedFor = binding.etCourseSuitedFor.text.toString()
            val courseImageLink = binding.etCourseImageLink.text.toString()
            val courseLink = binding.etCourseLink.text.toString()
            val courseDescription = binding.etCourseDescription.text.toString()
            
            if (courseName.isEmpty() || courseSuitedFor.isEmpty() || courseImageLink.isEmpty() || courseLink.isEmpty() || courseDescription.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            }
            else {
                val courseRVModal = TipsRVModel(courseName, courseSuitedFor, courseImageLink, courseLink, courseDescription)

                databaseReference.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        databaseReference.child(courseName).setValue(courseRVModal)
                        Toast.makeText(this@AddTipsActivity, "Tips Added!!!", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@AddTipsActivity,
                            "Error: $error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }
}