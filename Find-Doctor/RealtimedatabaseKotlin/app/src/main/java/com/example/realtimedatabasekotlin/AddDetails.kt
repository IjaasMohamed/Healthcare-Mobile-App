package com.example.realtimedatabasekotlin

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.realtimedatabasekotlin.databinding.ActivityAddDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddDetails : AppCompatActivity() {


    private lateinit var binding: ActivityAddDetailsBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, AddDetails::class.java)
            startActivity(intent)

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
        binding.uploadBtn.setOnClickListener {
            val intent = Intent(ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        binding.addBtn.setOnClickListener {
            adddata()
        }


    }


    var selectedImageUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imgDoctor.setBackgroundDrawable(bitmapDrawable)

        }
    }

    private fun adddata() {
        val name = binding.nameTxt.text.trim().toString()
        val number = binding.numberTxt.text.trim().toString()
        val date = binding.dateTxt.text.trim().toString()
        val special = binding.specialTxt.text.trim().toString()
        var imgID: String = ""

        database = FirebaseDatabase.getInstance().getReference("Doctors")
        val empId = database.push().key!!
        if (selectedImageUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")
        ref.putFile(selectedImageUri!!)
            .addOnSuccessListener {taskSnapshot ->
                Toast.makeText(this, "Photo saved", Toast.LENGTH_SHORT).show()
                    taskSnapshot.metadata!!.reference!!.downloadUrl
            .addOnSuccessListener { uri ->
                imgID=uri.toString()
                Log.i("Firebase image URL","$imgID")
                val doctor = doctor(empId, name, number, special, date, imgID)
                database.child(empId).setValue(doctor).addOnSuccessListener {




                    val intent = Intent(this@AddDetails, ViewDatas::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener {

                    Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show()


                }
                Toast.makeText(this, " atttched image to database $imgID", Toast.LENGTH_SHORT).show()
            }}.addOnFailureListener {
            Toast.makeText(this, "Failed to atttch image to database", Toast.LENGTH_SHORT).show()
        }
        Log.i("Firebase image URL","$imgID")


    }


}