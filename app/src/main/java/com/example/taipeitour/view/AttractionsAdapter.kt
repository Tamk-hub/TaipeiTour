package com.example.taipeitour.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taipeitour.R
import com.example.taipeitour.model.Attraction

class AttractionsAdapter(private var emplist: List<Attraction>) : RecyclerView.Adapter<AttractionsAdapter.MyViewHolder>() {
    // This method creates a new ViewHolder object for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for each item and return a new ViewHolder object
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.items_list, parent, false)
        return MyViewHolder(itemView)
    }

    // This method returns the total
    // number of items in the data set
    override fun getItemCount(): Int {
        return emplist.size
    }

    // This method binds the data to the ViewHolder object
    // for each item in the RecyclerView
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEmp = emplist[position]
        holder.title.text = currentEmp.name
        holder.content.text = currentEmp.introduction
        // 使用 Glide 載入圖片
        Glide.with(holder.itemView)
            .load(currentEmp.image)
            .into(holder.image)


        holder.itemView.setOnClickListener{
            val bundle = Bundle().apply {
                putString("attractionName", currentEmp.name)
                putString("attractionIntroduction", currentEmp.introduction)
                putString("attractionImage", currentEmp.image)
                putString("attractionOpen_time", currentEmp.open_time)
                putString("attractionAddress", currentEmp.address)
                putString("attractionTel", currentEmp.tel)
                putString("attractionUrl", currentEmp.url)
                // 將其他需要傳遞的資料添加到這裡
            }
            it.findNavController().navigate(R.id.action_attractions_list_to_attraction_detail,bundle)
        }
    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val content: TextView = itemView.findViewById(R.id.tv_content)
        val image: ImageView = itemView.findViewById(R.id.ig_att)

    }
    fun updateData(newData: List<Attraction>) {
        emplist = newData
        notifyDataSetChanged()
    }
}