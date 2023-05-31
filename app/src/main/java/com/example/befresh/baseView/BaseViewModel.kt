package com.example.befresh.baseView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val viewStateInternalLiveData = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState>
        get() = viewStateInternalLiveData


    open fun loading(data: String? = null) {
        viewStateInternalLiveData.postValue(ViewState(State.LOADING, anyData = data))
    }

    open fun success(data: String? = null) {
        viewStateInternalLiveData.postValue(ViewState(State.SUCCESS, anyData = data))
    }

    open fun error(message: String? = null, data: String? = null, errorCode: Int? = null) {
        viewStateInternalLiveData.postValue(ViewState(State.ERROR, message, data, errorCode))
    }


    data class ViewState(
        val state: State,
        val message: String? = null,
        val anyData: String? = null,
        val errorCode: Int? = null,
    )

    enum class State {
        LOADING,
        SUCCESS,
        ERROR
    }

}