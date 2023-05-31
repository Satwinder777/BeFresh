package com.example.befresh.view.fragment.shop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.befresh.R
import com.example.befresh.view.fragment.explore.CheckModel

class ListShowAdapter(var list: ArrayList<CheckModel>) :
    RecyclerView.Adapter<ListShowAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkShow: CheckBox = itemView.findViewById(R.id.checkShow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_show_checkbox, parent, false)
        )
    }

    override fun onBindViewHolder(holder:ViewHolder,position:Int) {
        holder.apply {
//            checkShow.isChecked=list[position].select
//            checkShow.text=list[position].title
//            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}