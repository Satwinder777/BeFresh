package com.example.befresh.networkManager

import com.example.befresh.BuildConfig
import com.example.befresh.application.MyApplicationClass
import com.gocarhub.networkManager.APIInterface
import com.gocarhub.networkManager.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilder {

    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder().followRedirects(false).followSslRedirects(false).apply {

            addInterceptor(HeaderInterceptor(MyApplicationClass.getInstance()))
//            authenticator(TokenAuthenticator(CarHubApp.getInstance()))

            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            connectTimeout(5, TimeUnit.MINUTES)
            readTimeout(5, TimeUnit.MINUTES)
        }.build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): APIInterface =
        Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(gsonConverterFactory)
            client(okHttpClient)
        }.build().create(APIInterface::class.java)
}