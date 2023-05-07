package com.example.udemyclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.udemyclone.databinding.ActivityTipsDetailsBinding
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class TipsDetails : AppCompatActivity() {
    lateinit var binding: ActivityTipsDetailsBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var course: TipsRVModel

    lateinit var courseID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsDetailsBinding.inflate(layoutInflater)
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

        binding.btnEditCourse.setOnClickListener {
            val intent = Intent(this, EditTipsActivity::class.java)
            intent.putExtra("TipsID", courseID)
            startActivity(intent)
            finish()
        }

        //! remaining
        binding.btnDeleteCourse.setOnClickListener {
            databaseReference.removeValue()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}