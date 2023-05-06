package com.example.realtimedatabasekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList


class doctorAdaptor( private val doctorList: ArrayList<doctor>,private val context:Context) :
RecyclerView.Adapter<doctorAdaptor.MyViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.doctor_item,
            parent, false
        )
        return MyViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = doctorList[position]

        holder.name.text = currentitem.name
        holder.number.text = currentitem.number
        holder.special.text = currentitem.special
        holder.date.text = currentitem.date
        Glide.with(context)
            .load(doctorList[position].image)
            .into(holder.imageDoctor)
       // holder.image.setImageResource(currentitem.image(context)



    }

    override fun getItemCount(): Int {

        return doctorList.size
    }


    class MyViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.tvname)
        val number: TextView = itemView.findViewById(R.id.tvnumber)
        val special: TextView = itemView.findViewById(R.id.tvspecial)
        val date: TextView = itemView.findViewById(R.id.tvdate)
        val imageDoctor=itemView.findViewById<ImageView>(R.id.image_doctor)
//val image:ShapeableImageView=itemView.findViewById(R.id.image_doctor)
init {
    itemView.setOnClickListener {
        clickListener.onItemClick(adapterPosition)
    }
}
    }

}