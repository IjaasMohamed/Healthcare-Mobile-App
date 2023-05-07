package com.example.udemyclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.udemyclone.databinding.ActivityTipsDetailsBinding
import com.example.udemyclone.databinding.ActivityUserTipsDetailsBinding
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class UserTipsDetails : AppCompatActivity() {
    lateinit var binding: ActivityUserTipsDetailsBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var course: TipsRVModel

    lateinit var courseID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTipsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        courseID = intent.getStringExtra("TipsID").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Tips").child(courseID)

        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(TipsRVModel::class.java)?.let {
                    course = it
                    Picasso.get().load(course.tipsImageLink.toString()).into(binding.ivCourseImage)

                    binding.tvCourseTitle.text = course.tipsName.toString()
                    binding.tvCourseDescription.text = course.tipsDescription.toString()
                    val suitedFor = "Suited For: ${course.tipsSuitedFor.toString()}"
                    binding.tvSuitedFor.text = suitedFor
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }
        })



        //! remaining

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, UserViewTips::class.java))
        finish()
    }
}