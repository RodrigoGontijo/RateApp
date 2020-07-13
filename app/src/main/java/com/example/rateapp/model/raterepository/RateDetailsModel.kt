package com.example.rateapp.model.raterepository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class RateDetailsModel(

    @SerializedName("about")
    val about: String?,

    @SerializedName("adress")
    val adress: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("review")
    val review: Float?,

    @SerializedName("schedule")
    val schedule: Schedule?,

    @SerializedName("type")
    val type: String?
)


data class Schedule(
    @SerializedName("friday")
    val friday: Friday,

    @SerializedName("monday")
    val monday: Monday,

    @SerializedName("saturday")
    val saturday: Saturday,

    @SerializedName("sunday")
    val sunday: Sunday,

    @SerializedName("thursday")
    val thursday: Thursday,

    @SerializedName("tuesday")
    val tuesday: Tuesday,

    @SerializedName("wednesday")
    val wednesday: Wednesday
)

data class Friday(
    @SerializedName("close")
    val close: String,

    @SerializedName("open")
    val open: String
)

data class Monday(
    @SerializedName("close")
    val close: String,

    @SerializedName("open")
    val open: String
)

data class Saturday(
    @SerializedName("close")
    val close: String,

    @SerializedName("open")
    val open: String
)

data class Sunday(
    @SerializedName("close")
    val close: String,

    @SerializedName("open")
    val open: String
)

data class Thursday(
    @SerializedName("close")
    val close: String,

    @SerializedName("open")
    val open: String
)

data class Tuesday(
    @SerializedName("close")
    val close: String,

    @SerializedName("open")
    val open: String
)

data class Wednesday(
    @SerializedName("close")
    val close: String,

    @SerializedName("open")
    val open: String
)