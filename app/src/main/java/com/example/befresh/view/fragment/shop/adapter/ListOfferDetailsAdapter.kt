package com.example.befresh.view.fragment.shop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.befresh.R
import com.example.befresh.databinding.ListOfferDetailsBinding
import com.example.befresh.model.response.GetMenuItemResponse
import com.example.befresh.utils.CommonMethod

class ListOfferDetailsAdapter(
    var setClickOffer: onClickOffer,
    var list: ArrayList<GetMenuItemResponse.DataX>
) : RecyclerView.Adapter<ListOfferDetailsAdapter.ViewHolder>() {

    class ViewHolder(var binding: ListOfferDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListOfferDetailsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_offer_details,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {

            if (list[position].category_detail!=null)
            if (list[position].category_detail?.title.isNullOrEmpty().not()) {
                binding.tvDetailsName.text = list[position].category_detail?.title
            }

            binding.tvDetaislDes.text = list[position].category_detail?.description
            CommonMethod.LoadImage(binding.ivDetailsOfr, list[position].category_detail?.image)


            itemView.setOnClickListener {
                setClickOffer.onClickOfferItem(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

interface onClickOffer {
    fun onClickOfferItem(position: Int)
}