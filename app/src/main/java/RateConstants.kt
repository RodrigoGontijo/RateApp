package com.example.rateapp

interface RateConstants {
    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
        const val PREFS_TIME = "prefsTime"
        const val REFRESH_TIME = 5 * 60 * 1000 * 1000 * 1000L
    }
}