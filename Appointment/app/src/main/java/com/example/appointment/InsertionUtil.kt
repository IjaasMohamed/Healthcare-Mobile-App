package com.example.appointment

import com.example.appointment.models.Model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object InsertionUtil {
    private lateinit var dbRef : DatabaseReference

    fun insertData(empName: String, empAge: String, docName: String) {
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        if(empName.isEmpty()){
            throw IllegalArgumentException("Please enter Patient Name")
        }
        if (empAge.isEmpty()){
            throw IllegalArgumentException("Please enter Patient's Age")
        }
        if (docName.isEmpty()){
            throw IllegalArgumentException("Please enter the name of assigned Doctor")
        }

        val empId = dbRef.push().key!!

        val employee = Model(empId, empName,empAge, docName )

        //child here creates a copy with using the id
        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener{
                // Handle completion
            }
    }
}