package com.example.befresh.baseView
import com.example.befresh.R
import com.example.befresh.application.MyApplicationClass
import com.example.befresh.model.ResponseWrapper
import com.gocarhub.model.BaseResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException

open class BaseRepository {
    suspend fun <T> baseApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ResponseWrapper<Any?> {
        return withContext(dispatcher) {
            try {
                ResponseWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    is ConnectException -> {
                        ResponseWrapper.Error(
                            MyApplicationClass.getInstance()
                                .getString(R.string.error_no_internet_connection)
                        )
                    }
                    is HttpException -> {
                        val errorResponse = convertErrorBody(throwable)
                        if (errorResponse != null) {
                            ResponseWrapper.Error(errorResponse.message, errorResponse.status)
                        } else {
                            ResponseWrapper.Error(
                                MyApplicationClass.getInstance()
                                    .getString(R.string.error_something_went_wrong), errorResponse
                            )
                        }
                    }
                    else -> {

                        ResponseWrapper.Error(
                            MyApplicationClass.getInstance()
                                .getString(R.string.error_something_went_wrong)
                        )
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): BaseResponseModel? {
        return try {

            throwable.response()?.errorBody()?.string().let {
                Gson().fromJson(it, BaseResponseModel::class.java)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

}