package com.example.befresh.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class CustomNonScroll(context: Context?) : LinearLayoutManager(context) {
    private var isScrollEnabled = true
    fun setScrollEnabled(flag: Boolean) {
        isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return  isScrollEnabled && super.canScrollHorizontally()
    }
}