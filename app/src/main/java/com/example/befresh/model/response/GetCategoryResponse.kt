package com.example.befresh.model.response

import com.example.befresh.model.request.CategoryData

data class GetCategoryResponse(
    val `data`: List<CategoryData>,
    val message: String,
    val status: Int,
    val success: Boolean
)