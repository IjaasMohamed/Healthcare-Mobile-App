package com.example.realtimedatabasekotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.realtimedatabasekotlin.databinding.ActivityAdminBinding
import com.google.firebase.database.DatabaseReference

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addDetails.setOnClickListener {
            val intent = Intent(this, AddDetails::class.java)
            startActivity(intent)

        }
        binding.viewDetails.setOnClickListener {
            val intent = Intent(this, ViewDatas::class.java)
            startActivity(intent)

        }

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, AddDetails::class.java)
            startActivity(intent)

        }

        binding.viewBtn.setOnClickListener {
            val intent = Intent(this, ViewDatas::class.java)
            startActivity(intent)

        }

        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.logoutBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}