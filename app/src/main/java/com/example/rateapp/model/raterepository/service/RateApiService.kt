package com.example.rateapp.model.raterepository.service

import com.example.rateapp.model.raterepository.RateModel
import com.example.rateapp.model.raterepository.api.RateApi
import io.reactivex.Single

class RateApiService(private val api: RateApi) {

    fun getRateList(): Single<List<RateModel>> {
        return api.getRateList()
    }
}