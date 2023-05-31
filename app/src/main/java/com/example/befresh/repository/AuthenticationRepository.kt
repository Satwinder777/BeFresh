package com.example.befresh.repository

import com.example.befresh.baseView.BaseRepository
import com.example.befresh.model.ResponseWrapper
import com.example.befresh.model.request.SignupRequest
import com.gocarhub.networkManager.APIInterface
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val apiInterface: APIInterface) :
    BaseRepository() {

    suspend fun signUp(signUpRequestModel: SignupRequest): ResponseWrapper<Any?> {
        return baseApiCall(Dispatchers.IO) {
            apiInterface.signUp(
                signUpRequestModel.name,
                signUpRequestModel.country_code,
                signUpRequestModel.mobile,
                signUpRequestModel.password,
                signUpRequestModel.user_type,
                signUpRequestModel.device_token,
                signUpRequestModel.device_type,
            )
        }
    }

    suspend fun otp(map: HashMap<String, Any>): ResponseWrapper<Any?> {
        return baseApiCall(Dispatchers.IO) {
            apiInterface.otp(map)
        }
    }

    suspend fun login(map: HashMap<String, Any>): ResponseWrapper<Any?> {
        return baseApiCall(Dispatchers.IO) {
            apiInterface.login(map)
        }
    }
}