package com.example.befresh.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.befresh.R
import com.example.befresh.application.MyApplicationClass
import com.example.befresh.baseView.BaseViewModel
import com.example.befresh.model.ResponseWrapper
import com.example.befresh.model.response.GetMenuItemResponse
import com.example.befresh.model.response.SignUpResponse
import com.example.befresh.repository.ProductHomeRepository
import com.gocarhub.model.BaseCallbackState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductHomeViewModel @Inject constructor(private val repository: ProductHomeRepository) :
    BaseViewModel() {

    private var getMenuItemMutable = MutableLiveData<BaseCallbackState>()
    val getMenuItemLive: LiveData<BaseCallbackState> get() = getMenuItemMutable

    fun getMenuItemViewModel() {
        viewModelScope.launch(Dispatchers.IO) {
            loading()
            try {
                when (val serverResponse = repository.getMaunItem()) {
                    is ResponseWrapper.Success -> {
                        val response = serverResponse.value as GetMenuItemResponse
                        if (response.success) {
                            success()
                            getMenuItemMutable.postValue(
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