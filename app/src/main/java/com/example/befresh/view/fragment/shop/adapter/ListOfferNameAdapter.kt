package com.example.befresh.view.fragment.shop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.befresh.R
import com.example.befresh.databinding.ListOfferNameBinding
import com.example.befresh.model.response.GetMenuItemResponse

class ListOfferNameAdapter(
    var onOfferClick: OnClickOfferItem,
    var list: ArrayList<GetMenuItemResponse.DataX>
) :
    RecyclerView.Adapter<ListOfferNameAdapter.ViewHolder>() {

    lateinit var mAdapter: ListOfferDetailsAdapter

    class ViewHolder(var binding: ListOfferNameBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(listFruits: List<GetMenuItemResponse.DataX>) {
        this.list = listFruits as ArrayList<GetMenuItemResponse.DataX>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListOfferNameBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_offer_name,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            binding.tvOfferName.text = list[position].title
            mAdapter = ListOfferDetailsAdapter(
                object : onClickOffer {
                    override fun onClickOfferItem(position: Int) {
                        onOfferClick.onOfferClickItem()
                    }

                },
                list
            )
            binding.rvOfferDetails.setHasFixedSize(true)
            binding.rvOfferDetails.layoutManager = GridLayoutManager(itemView.context, 2)
            binding.rvOfferDetails.adapter = mAdapter
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}

interface OnClickOfferItem {
    fun onOfferClickItem()
}
