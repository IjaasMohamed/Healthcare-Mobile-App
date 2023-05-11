package com.example.appointment.activities

import android.content.Intent
import android.os.Bundle
import android.view.Display.Mode
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.R
import com.example.appointment.adapters.EmpAdapter
import com.example.appointment.models.Model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.Locale

class FetchingActivity : ComponentActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<Model>
    private lateinit var tempArrayList: ArrayList<Model>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<Model>()

        tempArrayList = arrayListOf<Model>()

        getEmployeesData()

    }

    private fun getEmployeesData() {

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(Model::class.java)
                        empList.add(empData!!)
                    }
                    empList.sortBy {
                        it.empId
                    }

                    tempArrayList.addAll(tempArrayList)

                    val mAdapter = EmpAdapter(empList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, EmployeeDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("empId", empList[position].empId)
                            intent.putExtra("empName", empList[position].empName)
                            intent.putExtra("empAge", empList[position].empAge)
                            intent.putExtra("etDoctorName", empList[position].docName)
                            startActivity(intent)
                        }

                    })


                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)
        val item  = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())

                if (searchText.isNotEmpty()){
                    empList.forEach{

                        if (it.empName?.toLowerCase(Locale.getDefault())!!.contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }
                    empRecyclerView.adapter!!.notifyDataSetChanged()
                }
                else {
                    tempArrayList.clear()
                    tempArrayList.addAll(empList)
                    empRecyclerView.adapter!!.notifyDataSetChanged()
                }


                return false
            }


        })

        return super.onCreateOptionsMenu(menu)
    }
}
