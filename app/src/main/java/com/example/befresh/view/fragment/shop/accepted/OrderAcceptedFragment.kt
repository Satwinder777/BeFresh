package com.example.befresh.view.fragment.shop.accepted

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.befresh.MainActivity
import com.example.befresh.R
import com.example.befresh.databinding.FragmentOrderAcceptedBinding
import com.example.befresh.baseView.BaseFragment


class OrderAcceptedFragment : BaseFragment<FragmentOrderAcceptedBinding>() {


    override fun getLayoutResID(): Int {
        return R.layout.fragment_order_accepted
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).isBottomBar(false)

        binding.btnTrack.setOnClickListener {
            showCustomAlert()
        }
    }

    private fun showCustomAlert() {
        val dialogView = layoutInflater.inflate(R.layout.list_track_order_dailog, null)
        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.window!!.decorView
            .setBackgroundResource(android.R.color.transparent)
        val btDismiss = dialogView.findViewById<Button>(R.id.btnTrackDailog)
        btDismiss.setOnClickListener {
            customDialog.dismiss()
        }
    }

    override fun setUpUi(binding: FragmentOrderAcceptedBinding) {


    }


}