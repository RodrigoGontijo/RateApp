package com.example.rateapp.viewmodel


import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rateapp.RateConstants.Companion.PREFS_TIME
import com.example.rateapp.model.raterepository.RateListModel
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



    val rates = MutableLiveData<RateListModel>()
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
                .subscribeWith(object: DisposableSingleObserver<RateListModel>(){
                    override fun onSuccess(rateList: RateListModel) {
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

    private suspend fun ratesRetrieved(rate: List<RateModel>) {
        viewModelScope.launch {
            rateDao.getAllRates()
            val result = rateDao.getAllRates()
            val tempRatesListModel = RateListModel(rate)
            rates.value = tempRatesListModel
            ratesLoadError.value = false
            loading.value = false
        }
    }

    private fun storeRatesLocal(list: RateListModel){
        viewModelScope.launch {
            rateDao.deleteAllRates()
            val result = list.rateListModel.toTypedArray().let { rateDao.insertAll(*it) }
            var i = 0
            while(i<list.rateListModel.size){
                list.rateListModel[i].id = result[i].toInt()
                i++
            }
            ratesRetrieved(list.rateListModel)
        }

        timeSharedPreferences.edit(commit = true){ putLong(PREFS_TIME, System.nanoTime()) }
    }

}