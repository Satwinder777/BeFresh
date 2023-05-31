package com.example.befresh.view.fragment.addCart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.befresh.R
import com.example.befresh.databinding.FragmentAddCartBinding
import com.example.befresh.baseView.BaseFragment
import com.example.befresh.view.fragment.cart.CartFragment

class AddCartFragment : BaseFragment<FragmentAddCartBinding>() {

    var a:Int = 1
    var totalCost = 0




    override fun getLayoutResID(): Int {
        return R.layout.fragment_add_cart
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sliderImageSet()
    }

    private fun sliderImageSet() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(
            SlideModel(
                "https://bit.ly/2YoJ77H",
                "",
                ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(
            SlideModel(
                "https://bit.ly/2BteuF2",
                "",
                ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "", ScaleTypes.CENTER_CROP))

//        binding.imageSlider.setImageList(imageList)
//        binding.imageSlider.setItemClickListener(object : ItemClickListener {
//            override fun onItemSelected(position: Int) {
//                // You can listen here
////                binding.imageSlider.startSliding(3000) // with new period
////                binding.imageSlider.startSliding()
////                binding.imageSlider.stopSliding()
//            }
//        }
//        )
    }

    override fun setUpUi(binding: FragmentAddCartBinding) {
        var img:Boolean = false
        binding.checkimageView1.setOnClickListener {    if( img ==false){
             binding.checkimageView1.setImageResource(R.drawable.heart1_24)
             img =true
        }
            else{
             binding.checkimageView1.setImageResource(R.drawable.like_24)
             img=false
        }
       }


//        binding.star1.setOnClickListener { binding.star1.setImageResource(R.drawable.golden_star_24) }
//        binding.star2.setOnClickListener { binding.star2.setImageResource(R.drawable.golden_star_24) }
//        binding.star3.setOnClickListener { binding.star3.setImageResource(R.drawable.golden_star_24) }
//        binding.star4.setOnClickListener { binding.star4.setImageResource(R.drawable.golden_star_24) }
//        binding.star5.setOnClickListener {  binding.star5.setImageResource(R.drawable.golden_star_24)}

        binding.button7.setOnClickListener {
            a+=1
            binding.textView21.text = a.toString()
            var toDolar =  82
             totalCost = a*toDolar
            binding.textView22.text = totalCost.toString()
            Log.e("helloworld", "setUpUi: increement works"+a )
        }
        binding.button5.setOnClickListener {

            if (a>0) {
                a--
                binding.textView21.text = a.toString()
                var toDolar =  82
                 totalCost = a*toDolar
                binding.textView22.text = totalCost.toString()
                Log.e("helloworld", "setUpUi: decreement works" + a)
            }
        }
        binding.button8.setOnClickListener {
            val msg = binding.ratingBar.rating.toString()
            Toast.makeText(this.context, "You Rate :${msg} and cost is:${totalCost}", Toast.LENGTH_SHORT).show()

        }
        binding.imageView4.setOnClickListener {
//             findNavController().navigate(R.id.container)
            findNavController().navigate(R.id.shopFragment)
        }

        }




    }



