package com.example.befresh.utils

 object  AppConstants {
     const val GPS_LOCATION = 3001
     const val AppPassword="ATBB9hhKVPN8NGVQRyvpKqUPbncq967A589C"
     enum class AUTHORIZATION(val value:String ){
         KEY_AUTHORIZATION("Authorization"),
     }

     enum class USER_TYPE(val value:String ){
         SELLER("seller"),
         BUYER("buyer")
     }

     enum class SOICAL_LOGIN(val value: String){
         FACEBOOK( "1"),
         GOOGLE("2")
     }

     enum class CARS_DATA(val value: String){
        BUYERS_DATA("buyers_data"),
         SELLERS_DATA("sellers_data"),
         ITEM_POS("item_pos"),
         CAR_ID("car_id"),
         ADD_CAR("add_car")
     }
     enum class PAYMENT(val value: String){
         SUBSCRIPTION_DATA("subscription_data"),
         CARD("card"),
         ADD_CARD("add_card")

     }

     enum class SEARCH(val value: String){
         SEARCH_TEXT("search_text")

     }

    object REQUEST_CODE {
        const val  RC_GET_API = 100
        const val  RC_POST_API = 101
        const val GMAIL_SIGN_IN = 101 //"gmail_login"
        const val AUTOCOMPLETE_REQUEST_CODE = 102 //"gmail_login"
        const val FROM_SCREEN="from_screen"
        const val CAR_DETAILS="car_details"
        const val PROFILE="profile"
        const val SHIPPING="shipping"
        const val PICKUP="pickup"
        const val DASHBOARD_TAB="dashboard_tab"
        const val TAB_NUMBER="tab_number"
        const val SEARCH_BAR_All="search_bar_all"
        const val SEARCH_BAR_PICKUP="search_bar_pickup"
        const val SEARCH_BAR_SHIPPING="search_bar_shipping"
        const val SEARCH_TEXT="search_text"
        const val PAYMENT="payment"
        const val IS_SEARCH="is_search"
    }

    object API {

        object REQUEST_NAME {

            const val  GET_MOVIE = "get_movie_list"
            const val  LOGIN = "login"

        }
    }

     enum class ChatConstants(val value:String){
         GIVER_ID("giver_id"),
         RECEIVER_ID("receiver_id"),
         GIFT_ID("gift_id"),
         CHAT_USER_NAME("chat_user_name"),
         CHAT_USER_IMAGE("chat_user_image")
     }

}