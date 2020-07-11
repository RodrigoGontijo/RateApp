package com.example.rateapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rateapp.model.raterepository.RateModel
import com.example.rateapp.model.raterepository.dao.RateDao
import kotlinx.coroutines.launch

class DetailsRateViewModel(
    private val rateDao: RateDao
) : BaseViewModel() {

    val rateLiveData = MutableLiveData<RateModel>()


    fun fetch(rateId: Int) {
        viewModelScope.launch {
            val rate = rateDao.getRate(rateId)
            rateLiveData.value = rate
        }
    }

}