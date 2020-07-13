package com.example.rateapp.view

import android.view.View

interface RateItemClickListener {
    fun onItemRateClick(v: View, imageUrl: String, rateId: Int)
}