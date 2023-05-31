package com.gocarhub.networkManager


import com.example.befresh.model.response.GetCategoryResponse
import com.example.befresh.model.response.GetMenuItemResponse
import com.example.befresh.model.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
interface APIInterface {
    companion object {
        const val LOGIN = "login"
        const val SOCIAL_LOGIN = "social-login"
        const val SIGN_UP = "signup"
        const val OTP = "verify-otp"
        const val GET_MENU_ITEM = "get-menu-item"
        const val GET_Category = "get-category"
    }

    @Multipart
    @POST(SIGN_UP)
    suspend fun signUp(
        @Part("name") name: RequestBody?,
        @Part("country_code") country_code: RequestBody?,
        @Part("mobile") mobile: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("user_type") user_type: RequestBody?,
        @Part("device_token") device_token: RequestBody?,
        @Part("device_type") device_type: RequestBody?
    ): SignUpResponse

    @POST(OTP)
    suspend fun otp(@Body map:HashMap<String,Any>): SignUpResponse

    @POST(LOGIN)
    suspend fun login(@Body map:HashMap<String,Any>): SignUpResponse

    @GET(GET_MENU_ITEM)
    suspend fun getMenuItem(): GetMenuItemResponse

    @GET(GET_Category)
    suspend fun getCategory(): GetCategoryResponse

}