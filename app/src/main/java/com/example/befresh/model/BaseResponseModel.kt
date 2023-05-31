package com.gocarhub.model

import com.google.gson.annotations.SerializedName

open class BaseResponseModel
 {
    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("status")
    val status: Int? = null

    @SerializedName("message")
    val message: String? = null
}

