package com.example.rateapp.model.raterepository.service

import com.example.rateapp.model.raterepository.RateDetailsModel
import com.example.rateapp.model.raterepository.RateListModel
import com.example.rateapp.model.raterepository.RateModel
import com.example.rateapp.model.raterepository.api.RateApi
import io.reactivex.Single

class RateApiService(private val api: RateApi) {

    fun getRateList(): Single<RateListModel> {
        return api.getRateList()
    }

    fun getRateDetails(customerId: Int): Single<RateDetailsModel> {
        return api.getRateDetails(customerId)
    }
}