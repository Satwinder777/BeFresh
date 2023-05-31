package com.example.befresh.view.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.befresh.MainActivity
import com.example.befresh.R
import com.example.befresh.databinding.FragmentSignupBinding
import com.example.befresh.baseView.BaseFragment
import com.example.befresh.baseView.BaseViewModel
import com.example.befresh.model.request.SignupRequest
import com.example.befresh.model.response.SignUpResponse
import com.example.befresh.utils.CommonMethod.showToast
import com.example.befresh.utils.PrefConstants
import com.example.befresh.utils.SharedPref
import com.example.befresh.utils.loaderManager
import com.example.befresh.viewModel.AuthenticationViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>() {
    var userType = "user"

    private val viewModel: AuthenticationViewModel by viewModels()

    override fun getLayoutResID(): Int {
        return R.layout.fragment_signup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniView()
        setObserver()
    }

    private fun iniView() {
        binding.tvLoginAccount.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            binding.apply {
                if (etName.text.toString().trim().isEmpty()) {
                    showToast(requireContext(), "sdlkn")
                } else {
                    val name = etName.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull())

                    val mobile = etPhoneNumber.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull())

                    val country_code = "91"
                        .toRequestBody("text/plain".toMediaTypeOrNull())

                    val password = etPasswordSignUp.text.toString().trim()
                        .toRequestBody("text/plain".toMediaTypeOrNull())


                    val userType = userType
                        .toRequestBody("text/plain".toMediaTypeOrNull())

                    val deviceToken =
                        "fdsgdfgdsgfdsgfdgffd".toRequestBody("text/plain".toMediaTypeOrNull())
                    val deviceType = "android".toRequestBody("text/plain".toMediaTypeOrNull())


                    val signUpRequestModel = SignupRequest()
                    signUpRequestModel.apply {
                        this.name = name
                        this.mobile = mobile
                        this.country_code = country_code
                        this.password = password
                        this.user_type = userType
                        this.device_token = deviceToken
                        this.device_type = deviceType
                    }

                    viewModel.signUp(signUpRequestModel)
                }
            }
            // findNavController().navigate(R.id.signUpFragment)
        }
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
                                showToast(requireContext(), it1)
                                Log.e("messageObseve", "showToast,message")
                            }
                        }
                    }
                }
                signupLive.observe(requireActivity()) {
                    when (it.success) {
                        true -> {
                            val mFragment = Fragment()
                            val bundle = Bundle()
                            bundle.putString("Key", binding.etPhoneNumber.text.toString())
                            mFragment.arguments = bundle

                            if (userType == "user") {
                                findNavController().navigate(R.id.otpFragment, bundle)
                            } else {
                                startActivity(Intent(requireContext(), MainActivity::class.java))
                                requireActivity().finish()
                            }
                        }
                        else -> {
                            Log.e("messageObseve", it.message.toString())
                        }
                    }
                }
            }
        }
    }

    override fun setUpUi(binding: FragmentSignupBinding) {
    }


}