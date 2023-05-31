package com.example.befresh.model.response

import com.gocarhub.model.BaseResponseModel
import java.io.Serializable

data class SignUpResponse(
//    val message: String,
//    val status: Int,
//    val success: Boolean,
    val data: Data
) : BaseResponseModel() {
    data class Data(
        val access_token: String,
        val cart_count: Int,
        val country_code: Any,
        val created_at: String,
        val deleted_at: Any,
        val device_token: String,
        val device_type: String,
        val email: String,
        val facebook_id: Any,
        val google_id: Any,
        val id: Int,
        val image: String,
        val is_otp_verified: Boolean,
        val last_order_amount: Int,
        val lat: String,
        val lng: String,
        val mobile: String,
        val name: String,
        val otp: String,
        val otp_expire_time: Any,
        val status: String,
        val total_spent: Int,
        val updated_at: String,
        val user_type: String
    )
}