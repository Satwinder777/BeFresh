package com.example.befresh.view.fragment.cart.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.befresh.databinding.FruitViewItemBinding
import com.example.befresh.view.fragment.cart.classes.FruitData


class VegAdapter(private val fruitArrayList: ArrayList<FruitData>) :
    RecyclerView.Adapter<VegAdapter.InnerClass1>() {
    var sumTotal = 0
    private lateinit var stringV: String
    val v: MutableList<Int> = ArrayList<Int>()


    var k = "hi"


    private lateinit var binding: FruitViewItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            InnerClass1 {
        var binding =
            FruitViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerClass1(binding)


    }

    override fun getItemCount(): Int {
        return fruitArrayList.size
    }

    override fun onBindViewHolder(holder: InnerClass1, position: Int) {
        var c: Int
        val currentItem = fruitArrayList[position]
        var item: Int = 0


        holder.vegName.text = currentItem.vegetableName.toString()
        holder.vegWeight.text = currentItem.weight.toString()
        holder.vegImg.setImageResource(currentItem.vegImgage)
        holder.perVegPrize.text = currentItem.totalcost.toString()
        holder.totalItem.text = item.toString()
        holder.incBtn.setOnClickListener {


            if (item >= 0) {
                item += 1
                holder.totalItem.text = item.toString()

                var a = currentItem.totalcost.toInt()
                c = a * item
                holder.perVegPrize.text = c.toString()
                v.add(currentItem.totalcost)
                Log.e("thevalueis", "holder.addbtn" + v)
            } else {
                Toast.makeText(holder.endBtn.context, "new", Toast.LENGTH_SHORT).show()
            }

            sumTotal = v.reduce { total, next -> total + next }
        }

        holder.decBtn.setOnClickListener {
            try {
                if (item > 0  ) {
                item --
                holder.totalItem.text = item.toString()
                var a = currentItem.totalcost.toInt()
                c = a * item
                holder.perVegPrize.text = c.toString()
                v.remove(currentItem.totalcost)
                Log.e("thevalueis", "holder.decbtn" + v)
                sumTotal = v.reduce { total, next -> total + next }

            }
                // some code
            } catch (e:Exception) {
                Log.e("newsc", "onBindViewHolder: "+e.message.toString() )
            } finally {
                holder.totalItem.text = item.toString()
                Toast.makeText(holder.endBtn.context, "less", Toast.LENGTH_SHORT).show()
//                Log.e("thevalueis", "holder.decbtn else works" + v)

                // optional finally block
            }


//            else if (item==1 && v.size==null){
//                v.add(0)
//            }


        }


        holder.endBtn.setOnClickListener {

            val position = holder.adapterPosition
            val model = fruitArrayList[position]
            Log.e("adapterpostion", "holder.endbtn" + position)
            fruitArrayList.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(
                holder.vegName.context,
                "you deleted : ${holder.vegName.text}",
                Toast.LENGTH_SHORT
            ).show()


        }
        holder.vegImg.setOnClickListener {


            sumTotal = v.reduce { total, next -> total + next }
            // k = sum.toString()


            Log.e("thevalueis", "holder.img :" + sumTotal)


        }


//        Log.e("thevalueis", "Answer is :"+sum )
    }


    class InnerClass1(private val binding: FruitViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val vegName = binding.textView5
        val vegWeight = binding.textView6
        val incBtn = binding.button2
        val decBtn = binding.button
        val totalItem = binding.textView8
        val endBtn = binding.imageView7
        val perVegPrize = binding.textView7
        val vegImg = binding.imageView6


    }


}


