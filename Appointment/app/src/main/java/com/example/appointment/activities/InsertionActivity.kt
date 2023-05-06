package com.example.appointment.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.appointment.models.Model
import com.example.appointment.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : ComponentActivity() {

    private lateinit var etEmpName : EditText
    private lateinit var etEmpAge : EditText
    private lateinit var etDoctorName : EditText
    private lateinit var btnSaveData : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etDoctorName = findViewById(R.id.etDoctorName)
        btnSaveData =findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener(){
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData(){
        val empName = etEmpName.text.toString()
        val empAge = etEmpAge.text.toString()
        val docName = etDoctorName.text.toString()

        if(empName.isEmpty()){
            etEmpName.error = "Please enter Patient Name"
        }
        if (empAge.isEmpty()){
            etEmpAge.error = "Please enter Patient's Age"
        }
        if (docName.isEmpty()){
            etDoctorName.error = "Please enter the name of assigned Doctor"
        }
        val empId = dbRef.push().key!!

        val employee = Model(empId, empName,empAge, docName )

        //child here creates a copy with using the id
        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener{
                Toast.makeText(this,"Data Inserted Successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG ).show()
            }
    }
}
