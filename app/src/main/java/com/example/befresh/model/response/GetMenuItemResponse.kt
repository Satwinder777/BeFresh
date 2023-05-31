package com.example.befresh.model.response

import com.gocarhub.model.BaseResponseModel

data class GetMenuItemResponse(
    val data: Data,
/*    val message: String,
    val status: Int,
    val success: Boolean*/
) : BaseResponseModel() {

    data class Data(
        val current_page: Int,
        val data: List<DataX>,
        val first_page_url: String,
        val from: Int,
        val last_page: Int,
        val title:String,
        val last_page_url: String,
        val links: List<Link>,
        val next_page_url: String,
        val path: String,
        val per_page: Int,
        val prev_page_url: Any,
        val to: Int,
        val total: Int
    )

    data class DataX(
        val category_id: Int,
        val created_at: String,
        val deleted_at: Any,
        val description: String,
        val id: Int,
        val price: String,
        val title: String,
        var category_detail:CategoryList?=null,
        val updated_at: String,
        val user_id: Int
    )

    data class Link(
        val active: Boolean?=null,
        val label: String?=null,
        val url: Any?=null
    )

    data class CategoryList(
        val id:Int?=null,
        val user_id:Int?=null,
        val title:String?=null,
        val description:String?=null,
        val prize:String?=null,
        val image:String?=null

    )
}