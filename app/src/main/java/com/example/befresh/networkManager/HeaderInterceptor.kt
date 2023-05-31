package com.gocarhub.networkManager

import android.content.Context
import com.example.befresh.utils.AppConstants
import com.example.befresh.utils.PrefConstants
import com.example.befresh.utils.SharedPref
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader(
                    AppConstants.AUTHORIZATION.KEY_AUTHORIZATION.value,
                    "Bearer ".plus(SharedPref.instance.getString(PrefConstants.ACCESS_TOKEN.value).toString())
                )
            builder.addHeader(
                "Accept",
                "application/json")


        return chain.proceed(builder.build())
    }

}
