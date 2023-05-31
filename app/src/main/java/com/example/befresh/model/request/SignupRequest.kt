package com.example.befresh.model.request

import okhttp3.RequestBody

data class SignupRequest(
    var name: RequestBody? = null,
    var country_code: RequestBody? = null,
    var mobile: RequestBody? = null,
    var password: RequestBody? = null,
    var user_type: RequestBody? = null,
    var device_token: RequestBody? = null,
    var device_type: RequestBody? = null
)