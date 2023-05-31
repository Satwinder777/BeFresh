package com.example.befresh.view.fragment.cart

import android.util.Log
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.befresh.R
import com.example.befresh.baseView.BaseFragment
import com.example.befresh.databinding.FragmentCartBinding
import com.example.befresh.view.fragment.cart.Adapter.VegAdapter
import com.example.befresh.view.fragment.cart.classes.FruitData
import com.google.android.material.bottomsheet.BottomSheetDialog


class CartFragment : BaseFragment<FragmentCartBinding>() {
    private lateinit var btnText:String


    private lateinit var cartArrayList: ArrayList<FruitData>


    override fun setUpUi(binding: FragmentCartBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        showVegData()
        val  cartAdapter= VegAdapter(cartArrayList)
        binding.recyclerView.adapter = cartAdapter


//        binding.button2.setOnClickListener {
////        binding.button2.text = "PayNow ${ cartAdapter.sumTotal.toString() }"
//           val dialog = this.context?.let { it1 -> BottomSheetDialog(it1) }
//            Log.e("thevalueis", "btn event listen"+cartAdapter.sumTotal.toString() )
//
//            val view = layoutInflater.inflate(R.layout.bottom_popup, null)
//            val closebtn = view.findViewById<ImageView>(R.id.checkimageView)
//            closebtn.setOnClickListener { dialog?.dismiss() }
//
//            dialog?.setCancelable(false)
//            dialog?.setContentView(view)
//            dialog?.show()
//        }

        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.favouriteFragment)
        }
        binding.imageView4.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)

        }




    }

    override fun getLayoutResID(): Int {
       return R.layout.fragment_cart
    }




    private fun showVegData() {
        cartArrayList = ArrayList()
        val vegimg = arrayOf(
            R.drawable.simlamirch,
            R.drawable.patato,
            R.drawable.barcolli,
            R.drawable.onions,
            R.drawable.carrot,
            R.drawable.simlamirch,
            R.drawable.patato,
            R.drawable.barcolli,
            R.drawable.onions,
            R.drawable.carrot,
            R.drawable.simlamirch,
            R.drawable.patato,
            R.drawable.barcolli,
            R.drawable.onions,
            R.drawable.carrot,


            )

        val vegname = arrayOf(
            "Simlamirch",
            "Patato",
            "Barcolli",
            "Onions",
            "Carrot",
            "Simlamirch",
            "Patato",
            "Barcolli",
            "Onions",
            "Carrot",
            "Simlamirch",
            "Patato",
            "Barcolli",
            "Onions",
            "Carrot",

            )

        val vegweight = arrayOf(
            "1Kg, Prize",
            "2Kg, Prize",
            "3Kg, Prize",
            "2Kg, Prize",
            "1Kg, Prize",
            "5Kg, Prize",
            "1Kg, Prize",
            "2Kg, Prize",
            "3Kg, Prize",
            "2Kg, Prize",
            "1Kg, Prize",
            "5Kg, Prize",
            "1Kg, Prize",
            "2Kg, Prize",
            "3Kg, Prize",
            "2Kg, Prize",
            "1Kg, Prize",
            "5Kg, Prize",

            )


        val vegcost:  MutableList<Int> = ArrayList()
        for (i in 1..15){
            var a=  (1..20).random()
            a = a.toInt()
            vegcost.add(a.toInt())

        }

        for (i in vegimg.indices) {
            val vegData = FruitData(vegname[i], vegweight[i], vegcost[i], vegimg[i])
            cartArrayList.add(vegData)
        }

    }


}