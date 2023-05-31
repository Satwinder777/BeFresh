package com.example.befresh.view.fragment.explore

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.befresh.R
import com.example.befresh.model.request.CategoryData
import com.example.befresh.utils.CommonMethod

class ListItemAdapter(var onCheck: onClickChek,var list: ArrayList<CategoryData>):RecyclerView.Adapter<ListItemAdapter.ViewHolder>(){

    var select= false

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var imageView:ImageView = itemView.findViewById(R.id.IVImage)
        var tvTitle:TextView = itemView.findViewById(R.id.tv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_check,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
           CommonMethod.LoadImage(imageView, list[position].image)
            tvTitle.text=list[position].title
        }
    }

//    http://192.168.1.35:8021

    override fun getItemCount(): Int {
       return list.size
    }

    interface onClickChek{
        fun checkBoxCheck(list:CheckModel)
    }
}