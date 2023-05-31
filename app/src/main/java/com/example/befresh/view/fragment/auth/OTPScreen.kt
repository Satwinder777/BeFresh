package com.example.befresh.view.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.befresh.MainActivity
import com.example.befresh.R
import com.example.befresh.databinding.FragmentOTPScreenBinding
import com.example.befresh.baseView.BaseFragment
import com.example.befresh.baseView.BaseViewModel
import com.example.befresh.model.response.SignUpResponse
import com.example.befresh.utils.CommonMethod
import com.example.befresh.utils.PrefConstants
import com.example.befresh.utils.SharedPref
import com.example.befresh.utils.loaderManager
import com.example.befresh.viewModel.AuthenticationViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPScreen : BaseFragment<FragmentOTPScreenBinding>() {
    var otlAll = ""

    private val viewModel: AuthenticationViewModel by viewModels()

    override fun getLayoutResID(): Int {
        return R.layout.fragment_o_t_p_screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val index = args?.getString("Key")
        binding.tvOtpSent.text = index

        iniView()
        setObserver()


        Log.e("lsdjknvkjdsn", otlAll)
    }


    private fun iniView() {
        binding.btnVerify.setOnClickListener {
            val map = HashMap<String, Any>()
            map["mobile"] = binding.tvOtpSent.text.toString()
            map["otp"] = "1111"

            Log.e("lsdjknvkjdsn", otlAll)

            viewModel.otp(map)
        }


        setOtpFieldsConstraints()
    }


    private fun setObserver() {
        binding.apply {
            viewModel.apply {
                viewStateLiveData.observe(requireActivity()) {
                    when (it.state) {
                        BaseViewModel.State.LOADING -> {
                            showProgress(requireContext())
                            requireContext().loaderManager(true)
                            Log.e("messageObseve", "showProgress")
                        }
                        BaseViewModel.State.SUCCESS -> {
                            dismissProgress()
                            Log.e("messageObseve", "dismissProgress")
                        }
                        BaseViewModel.State.ERROR -> {
                            dismissProgress()

                            it.message?.let { it1 ->
                                CommonMethod.showToast(requireContext(), it1)
                                Log.e("messageObseve", "showToast,message")
                            }
                        }
                    }
                }
                otpLive.observe(requireActivity()) {
                    when (it.success) {
                        true -> {
                            val response = it.response as SignUpResponse
                            SharedPref.instance.putString(
                                PrefConstants.ACCESS_TOKEN.value,
                                response.data.access_token
                            )
                            SharedPref.instance.putString(
                                PrefConstants.USER_DATA.value,
                                Gson().toJson(response.data)
                            )
                            SharedPref.instance.putString(
                                PrefConstants.USER_TYPE.value,
                                response.data.user_type
                            )

                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            requireActivity().finish()

                        }
                        else -> {
                            Log.e("messageObseve", it.message.toString())
                        }
                    }
                }
            }
        }
    }

    //*** this function will automatically handle the focus navigation of the all textFields used for OTP input
    private fun setOtpFieldsConstraints() {
        binding.apply {
            ETOtp1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(no1: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!no1.isNullOrEmpty() && ETOtp2.text!!.isEmpty()) {
                        ETOtp2.requestFocus()
                        otlAll = no1.toString()
                        Log.e("lsdjknvkjdsn", otlAll)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })

            ETOtp2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(no2: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!no2.isNullOrEmpty() && ETOtp3.text!!.isEmpty()) {
                        ETOtp3.requestFocus()
                        otlAll = no2.toString()
                        Log.e("lsdjknvkjdsn", otlAll)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })

            ETOtp3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(no3: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!no3.isNullOrEmpty() && ETOtp4.text!!.isEmpty()) {
                        ETOtp4.requestFocus()
                        otlAll = no3.toString()
                        Log.e("lsdjknvkjdsn", otlAll)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
            ETOtp4.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(no4: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    if(!p0.isNullOrEmpty()&& ETOtp4.text!!.isEmpty()){
//                        ETOtp4.requestFocus()
//                    }
                    otlAll = no4.toString()
                    Log.e("lsdjknvkjdsn", otlAll)
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })


            ETOtp2.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (ETOtp2.text!!.isEmpty()) {
                        if (event.action == KeyEvent.ACTION_DOWN) {
                            ETOtp1.requestFocus()
                            ETOtp1.text!!.clear()
                        }
                    }

                }
                false
            }

            ETOtp3.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (ETOtp3.text!!.isEmpty()) {
                        if (event.action == KeyEvent.ACTION_DOWN) {
                            ETOtp2.requestFocus()
                            ETOtp2.text!!.clear()
                        }
                    }
                }
                false
            }
            ETOtp4.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (ETOtp4.text!!.isEmpty()) {
                        if (event.action == KeyEvent.ACTION_DOWN) {
                            ETOtp3.requestFocus()
                            ETOtp3.text!!.clear()
                        }
                    }
                }
                false
            }
        }
    }

    override fun setUpUi(binding: FragmentOTPScreenBinding) {
    }
}