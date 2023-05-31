package com.example.befresh.view.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.befresh.MainActivity
import com.example.befresh.R
import com.example.befresh.databinding.FragmentLoginBinding
import com.example.befresh.databinding.FragmentSignupBinding
import com.example.befresh.utils.CommonMethod
import com.example.befresh.baseView.BaseFragment
import com.example.befresh.baseView.BaseViewModel
import com.example.befresh.model.response.SignUpResponse
import com.example.befresh.utils.PrefConstants
import com.example.befresh.utils.SharedPref
import com.example.befresh.utils.loaderManager
import com.example.befresh.viewModel.AuthenticationViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener {
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun getLayoutResID(): Int {
        return R.layout.fragment_login
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setObserver()
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
                signupLive.observe(requireActivity()) {
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

    private fun initView() {
        binding.btnLogin.setOnClickListener {

        }

        binding.tvCreateAccount.setOnClickListener {

        }

    }


    override fun setUpUi(binding: FragmentLoginBinding) {
        binding.btnLogin.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){

            R.id.btn_login ->{
                val map = HashMap<String, Any>()
                map["mobile"] = binding.etPasswordLogin
                map["password"] = binding.etEmailLogin
                viewModel.login(map)
            }

            R.id.tvCreate_account ->{
                findNavController().navigate(R.id.signUpFragment)
            }
        }
    }
}