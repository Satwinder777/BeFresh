package com.example.befresh.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gocarhub.model.BaseCallbackState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.befresh.R
import com.example.befresh.application.MyApplicationClass
import com.example.befresh.baseView.BaseViewModel
import com.example.befresh.model.ResponseWrapper
import com.example.befresh.model.request.SignupRequest
import com.example.befresh.model.response.SignUpResponse
import com.example.befresh.repository.AuthenticationRepository
import kotlinx.coroutines.launch

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : BaseViewModel() {

    private var signupMutable = MutableLiveData<BaseCallbackState>()
    val signupLive: LiveData<BaseCallbackState> get() = signupMutable

    private var otpMutable = MutableLiveData<BaseCallbackState>()
    val otpLive: LiveData<BaseCallbackState> get() = otpMutable

    private var userInfoMutable = MutableLiveData<BaseCallbackState>()
    val userInfoLive: LiveData<BaseCallbackState> get() = userInfoMutable


    private var responseMutable = MutableLiveData<BaseCallbackState>()
    val responseLive: LiveData<BaseCallbackState> get() = responseMutable


    fun signUp(signUpRequestModel: SignupRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            loading()
            try {
                when (val serverResponse = repository.signUp(signUpRequestModel)) {
                    is ResponseWrapper.Success -> {
                        val response = serverResponse.value as SignUpResponse
                        if (response.success) {
                            Log.d("checkLogin", "success 1:")
                            success()
                            signupMutable.postValue(
                                BaseCallbackState(
                                    isLoading = false,
                                    success = response.success,
                                    response = response
                                )
                            )
                        } else {
                            error(response.message)
                        }
                    }
                    is ResponseWrapper.Error -> {
                        error(serverResponse.value as String?)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
//                Log.e("dkj",ex.message.toString())
                error(
                    MyApplicationClass.getInstance()
                        .getString(R.string.error_something_went_wrong)
                )
            }
        }
    }

    fun otp(map:HashMap<String, Any>) {
        viewModelScope.launch(Dispatchers.IO) {
            loading()
            try {
                when (val serverResponse = repository.otp(map)) {
                    is ResponseWrapper.Success -> {
                        val response = serverResponse.value as SignUpResponse
                        if (response.success) {
                            Log.d("checkLogin", "success 1:")
                            success()
                            otpMutable.postValue(
                                BaseCallbackState(
                                    isLoading = false,
                                    success = response.success,
                                    response = response
                                )
                            )
                        } else {
                            error(response.message)
                        }
                    }
                    is ResponseWrapper.Error -> {
                        error(serverResponse.value as String?)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
//                Log.e("dkj",ex.message.toString())
                error(
                    MyApplicationClass.getInstance()
                        .getString(R.string.error_something_went_wrong)
                )
            }
        }
    }

    fun login(map: HashMap<String, Any>){
        viewModelScope.launch(Dispatchers.IO){
            loading()
            try {
                when (val serverResponse = repository.login(map)) {
                    is ResponseWrapper.Success -> {
                        val response = serverResponse.value as SignUpResponse
                        if (response.success) {
                            Log.d("checkLogin", "success 1:")
                            success()
                            otpMutable.postValue(
                                BaseCallbackState(
                                    isLoading = false,
                                    success = response.success,
                                    response = response
                                )
                            )
                        } else {
                            error(response.message)
                        }
                    }
                    is ResponseWrapper.Error -> {
                        error(serverResponse.value as String?)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
//                Log.e("dkj",ex.message.toString())
                error(
                    MyApplicationClass.getInstance()
                        .getString(R.string.error_something_went_wrong)
                )
            }
        }
    }
}
