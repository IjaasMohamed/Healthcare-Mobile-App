package com.example.realtimedatabasekotlin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.realtimedatabasekotlin.databinding.ActivityEditDatasBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class editDatas : AppCompatActivity() {
    private lateinit var binding: ActivityEditDatasBinding
    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDatasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imagechangeBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, AddDetails::class.java)
            startActivity(intent)
            finish()
        }

        binding.viewBtn.setOnClickListener {
            val intent = Intent(this, ViewDatas::class.java)
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
        val empID = intent.getStringExtra("id")
        setValuesToViews()

        binding.updateBtn.setOnClickListener {

            val name = binding.nameTxt.text.toString()
            val number = binding.numberTxt.text.toString()
            val special = binding.specialTxt.text.toString()
            val date = binding.dateTxt.text.toString()


            if (empID != null) {
                val filename = UUID.randomUUID().toString()
                val ref = FirebaseStorage.getInstance().getReference("/image/$filename")
                ref.putFile(selectedImageUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        Toast.makeText(this, "Photo saved", Toast.LENGTH_SHORT).show()
                        taskSnapshot.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { uri ->
                                val image = uri.toString()
                                updateData(empID, name, special, number, date, image)
                                Toast.makeText(this, "Successfuly Updated", Toast.LENGTH_SHORT).show()
                            }
                    }}
        }
        binding.deleteBtn.setOnClickListener {


            database = FirebaseDatabase.getInstance().getReference("Doctors")
            database.child(empID!!).get().addOnSuccessListener {


                database.child(empID).removeValue().addOnSuccessListener {
                    Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                    binding.nameTxt.text = null
                    binding.numberTxt.text = null
                    binding.dateTxt.text = null
                    binding.specialTxt.text = null
                    binding.imgDoctor.setImageResource(R.drawable.ic_doctor_team_icon)
                }.addOnFailureListener {
                    Toast.makeText(this, "Errors in deleting the data.", Toast.LENGTH_SHORT)
                        .show()
                }

            }


        }
    }
    var selectedImageUri: Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            binding.imgDoctor.setImageDrawable(null)
            selectedImageUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            val bitmapDrawable = BitmapDrawable(bitmap)

            binding.imgDoctor.setBackgroundDrawable(bitmapDrawable)

        }
    }
    private fun setValuesToViews() {
        val empID=intent.getStringExtra("id")
        binding.nameTxt.setText(intent.getStringExtra("name"))
       binding.dateTxt.setText(intent.getStringExtra("date"))
        binding.numberTxt.setText(intent.getStringExtra("number"))
      binding.specialTxt.setText(intent.getStringExtra("special"))
        val imageUri=intent.getStringExtra("imageUri")
        Glide.with(this)
            .load(imageUri)
            .into(binding.imgDoctor)

    }

    private fun updateData(
        id:String,
        name: String,
        special: String,
        number: String,
        date: String,
        image:String,
    ) {

        database = FirebaseDatabase.getInstance().getReference("Doctors")
        val user = mapOf<String, String>(
            "id" to id,
            "name" to name,
            "number" to number,
            "special" to special,
            "date" to date,
            "image" to image
        )

        database.child(id).updateChildren(user).addOnSuccessListener {

            binding.nameTxt.text.clear()
            binding.specialTxt.text.clear()
            binding.numberTxt.text.clear()
            binding.dateTxt.text.clear()



        }.addOnFailureListener {

            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()

        }
    }
}