package com.example.udemyclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.example.udemyclone.databinding.ActivityEditTipsBinding
import com.google.firebase.database.*

class EditTipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTipsBinding

    private lateinit var courseID: String

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var course: TipsRVModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        courseID = intent.getStringExtra("TipsID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Tips").child(courseID)



        binding.btnCancel.setOnClickListener {
            val intent1 = Intent(this, TipsDetails::class.java)
            intent1.putExtra("TipsID", courseID)
            startActivity(intent1)
            finish()
        }

        fetchCourseDetails()

        binding.btnUpdateCourse.setOnClickListener {
            val courseName = binding.etCourseName.text.toString()
            val courseSuitedFor = binding.etCourseSuitedFor.text.toString()
            val courseImageLink = binding.etCourseImageLink.text.toString()
            val courseLink = binding.etCourseLink.text.toString()
            val courseDescription = binding.etCourseDescription.text.toString()

            if (courseName.isEmpty() ||  courseSuitedFor.isEmpty() || courseImageLink.isEmpty() || courseLink.isEmpty() || courseDescription.isEmpty()) {
                Toast.makeText(this, "Enter all fields first!!", Toast.LENGTH_SHORT).show()
            } else {
                binding.progressBar.visibility = View.VISIBLE
                databaseReference.removeValue()

                val course = TipsRVModel(
                    courseName,
                    courseSuitedFor,
                    courseImageLink,
                    courseLink,
                    courseDescription
                )
                databaseReference = firebaseDatabase.getReference("Tips").child(courseName)
                Handler(Looper.getMainLooper()).postDelayed({
                    databaseReference.setValue(course)
                    val intent = Intent(this, TipsDetails::class.java)
                    intent.putExtra("TipsID", course.tipsName)
                    startActivity(intent)
                    finish()
                }, 3000)
            }
        }
    }

    private fun fetchCourseDetails() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(TipsRVModel::class.java)?.let {
                    course = it
                    val courseName = course.tipsName
                    val courseSuitedFor = course.tipsSuitedFor
                    val courseImageLink = course.tipsImageLink
                    val courseLink = course.tipsLink
                    val courseDescription = course.tipsDescription

                    binding.etCourseName.setText(courseName)
                    binding.etCourseSuitedFor.setText(courseSuitedFor)
                    binding.etCourseImageLink.setText(courseImageLink)
                    binding.etCourseLink.setText(courseLink)
                    binding.etCourseDescription.setText(courseDescription)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditTipsActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent1 = Intent(this, TipsDetails::class.java)
        intent1.putExtra("TipsID", courseID)
        startActivity(intent1)
        finish()
    }
}