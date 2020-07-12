package com.example.rateapp.model.raterepository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RateModel(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String?,

    @ColumnInfo(name = "review")
    @SerializedName("review")
    var review: String?,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: String?
)


data class RateListModel(

    @SerializedName("listLocations")
    var rateListModel: List<RateModel>

)