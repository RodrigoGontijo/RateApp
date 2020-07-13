package com.example.rateapp.model.raterepository.api

import com.example.rateapp.model.raterepository.RateDetailsModel
import com.example.rateapp.model.raterepository.RateListModel
import com.example.rateapp.model.raterepository.RateModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RateApi {
    @GET("locations")
    fun getRateList(): Single<RateListModel>

    @GET("locations/{id}")
    fun getRateDetails(@Path("id") customerId: Int): Single<RateDetailsModel>
}