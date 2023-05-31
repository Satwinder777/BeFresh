package com.example.befresh.repository

import com.example.befresh.baseView.BaseRepository
import com.example.befresh.model.ResponseWrapper
import com.gocarhub.networkManager.APIInterface
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ProductHomeRepository @Inject constructor(private val apiInterface: APIInterface) :
    BaseRepository() {

    suspend fun getMaunItem(): ResponseWrapper<Any?> {
        return baseApiCall(Dispatchers.IO) {
            apiInterface.getMenuItem()
        }
    }
}