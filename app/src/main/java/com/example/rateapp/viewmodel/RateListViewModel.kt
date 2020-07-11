package com.example.rateapp.viewmodel

import RateConstants.Companion.PREFS_TIME
import RateConstants.Companion.REFRESH_TIME
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rateapp.model.raterepository.RateModel
import com.example.rateapp.model.raterepository.dao.RateDao
import com.example.rateapp.model.raterepository.service.RateApiService
import com.example.rateapp.notification.NotificationsHelper
import com.example.rateapp.util.TimeCacheUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class RateListViewModel(private val rateApiService: RateApiService,
                        private val rateDao: RateDao,
                        private val timeSharedPreferences: SharedPreferences,
                        private val notificationsHelper: NotificationsHelper,
                        private val timeCacheUtil: TimeCacheUtil
) : BaseViewModel(){



    val rates = MutableLiveData<List<RateModel>>()
    val ratesLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        timeCacheUtil.checkCacheDuration()
        val updatedTime = timeSharedPreferences.getLong(PREFS_TIME,0)
        if(updatedTime != null && updatedTime !=0L && System.nanoTime() - updatedTime < timeCacheUtil.getUpdateTime()){
            fetchFromDatabase()
        }else{
            fetchFromRemote()
        }
    }

    fun refreshBypassCache(){
        fetchFromRemote()
    }

    private fun fetchFromDatabase(){
        viewModelScope.launch {
            val rate = rateDao.getAllRates()
            ratesRetrieved(rate)
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            rateApiService.getRateList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<RateModel>>(){
                    override fun onSuccess(rateList: List<RateModel>) {
                        storeRatesLocal(rateList)
                        notificationsHelper.createNotification()
                    }

                    override fun onError(e: Throwable) {
                        ratesLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private suspend fun ratesRetrieved(ratesList: List<RateModel>) {
        viewModelScope.launch {
            rateDao.getAllRates()
            val result = rateDao.getAllRates()
            rates.value = ratesList
            ratesLoadError.value = false
            loading.value = false
        }
    }

    private fun storeRatesLocal(list: List<RateModel>){
        viewModelScope.launch {
            rateDao.deleteAllRates()
            val result = rateDao.insertAll(*list.toTypedArray())
            var i = 0
            while(i<list.size){
                list[i].id = result[i].toInt()
                i++
            }
            ratesRetrieved(list)
        }

        timeSharedPreferences.edit(commit = true){ putLong(PREFS_TIME, System.nanoTime()) }
    }

}