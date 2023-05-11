package com.example.appointment.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.SearchView
import androidx.activity.ComponentActivity
import com.example.appointment.R
import com.example.appointment.models.Model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var btnInsertData : Button
    private lateinit var btnFetchData : Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData = findViewById(R.id.btnFetchData)

        btnInsertData.setOnClickListener{
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }
        btnFetchData.setOnClickListener{
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }
    }


}

