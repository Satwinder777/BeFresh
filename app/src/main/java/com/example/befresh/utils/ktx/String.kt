package com.gocarhub.utils.ktx

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.ConvertDateTimeFormatFromServer():String?{
    return try {

        var spf=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        spf.timeZone= TimeZone.getTimeZone("UTC")
        val newDateTime=spf.parse(this)
        spf=SimpleDateFormat("dd-mm-yyy")
        spf.format(newDateTime)
    }catch (e:Exception){
        e.printStackTrace()
        ""
    }
}

@SuppressLint("SimpleDateFormat")
fun String.ConvertDayFromServer():String?{
    return try {

        var spf=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        spf.timeZone= TimeZone.getTimeZone("UTC")
        val newDateTime=spf.parse(this)
        spf=SimpleDateFormat("EEEE, ")
        spf.format(newDateTime)
    }catch (e:Exception){
        e.printStackTrace()
        ""
    }
}

@SuppressLint("SimpleDateFormat")
fun String.ConvertTimeFromServer():String?{
    return try {

        var spf=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        spf.timeZone= TimeZone.getTimeZone("UTC")
        val newDateTime=spf.parse(this)
        spf=SimpleDateFormat("hh:mm a")
        spf.format(newDateTime)
    }catch (e:Exception){
        e.printStackTrace()
        ""
    }
}

@SuppressLint("SimpleDateFormat")
fun convertPaymentEndDateFormat(daysDuration:Int): String? {
    return try {

        val calendar = Calendar.getInstance()
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        day=day.plus(daysDuration)

        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR))
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH,day)

        val currentDate = SimpleDateFormat("yyyy-MM-dd")
        val currentFormattedDate = currentDate.format(calendar.time)

        return currentFormattedDate

    } catch (e: Exception) {
        ""
    }
}