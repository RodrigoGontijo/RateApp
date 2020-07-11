package com.example.rateapp.model.raterepository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RateDetailsModel(

    @PrimaryKey
    @ColumnInfo(name = "about")
    @SerializedName("about")
    val about: String?,

    @ColumnInfo(name = "address")
    @SerializedName("address")
    val address: String?,

    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int?,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    val phone: String?,

    @ColumnInfo(name = "review")
    @SerializedName("review")
    val review: Int?,

    @ColumnInfo(name = "schedule")
    @SerializedName("schedule")
    val schedule: Schedule?,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    val type: String?
)

@Entity
data class Schedule(
    @ColumnInfo(name = "friday")
    @SerializedName("friday")
    val friday: Friday,

    @ColumnInfo(name = "monday")
    @SerializedName("monday")
    val monday: Monday,

    @ColumnInfo(name = "saturday")
    @SerializedName("saturday")
    val saturday: Saturday,

    @ColumnInfo(name = "sunday")
    @SerializedName("sunday")
    val sunday: Sunday,

    @ColumnInfo(name = "thursday")
    @SerializedName("thursday")
    val thursday: Thursday,

    @ColumnInfo(name = "tuesday")
    @SerializedName("tuesday")
    val tuesday: Tuesday,

    @ColumnInfo(name = "wednesday")
    @SerializedName("wednesday")
    val wednesday: Wednesday
)

@Entity
data class Friday(
    @ColumnInfo(name = "close")
    @SerializedName("close")
    val close: String,

    @ColumnInfo(name = "open")
    @SerializedName("open")
    val open: String
)

@Entity
data class Monday(
    @ColumnInfo(name = "close")
    @SerializedName("close")
    val close: String,

    @ColumnInfo(name = "open")
    @SerializedName("open")
    val open: String
)

@Entity
data class Saturday(
    @ColumnInfo(name = "close")
    @SerializedName("close")
    val close: String,

    @ColumnInfo(name = "open")
    @SerializedName("open")
    val open: String
)

@Entity
data class Sunday(
    @ColumnInfo(name = "close")
    @SerializedName("close")
    val close: String,

    @ColumnInfo(name = "open")
    @SerializedName("open")
    val open: String
)

@Entity
data class Thursday(
    @ColumnInfo(name = "close")
    @SerializedName("close")
    val close: String,

    @ColumnInfo(name = "open")
    @SerializedName("open")
    val open: String
)

@Entity
data class Tuesday(
    @ColumnInfo(name = "close")
    @SerializedName("close")
    val close: String,

    @ColumnInfo(name = "open")
    @SerializedName("open")
    val open: String
)

@Entity
data class Wednesday(
    @ColumnInfo(name = "close")
    @SerializedName("close")
    val close: String,

    @ColumnInfo(name = "open")
    @SerializedName("open")
    val open: String
)