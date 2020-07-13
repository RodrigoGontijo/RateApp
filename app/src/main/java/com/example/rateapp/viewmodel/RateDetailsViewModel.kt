package com.example.rateapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rateapp.model.raterepository.RateDetailsModel
import com.example.rateapp.model.raterepository.RateListModel
import com.example.rateapp.model.raterepository.RateModel
import com.example.rateapp.model.raterepository.dao.RateDao
import com.example.rateapp.model.raterepository.service.RateApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class RateDetailsViewModel(
    private val rateApiService: RateApiService
) : BaseViewModel() {

    val rate = MutableLiveData<RateDetailsModel>()
    val rateLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun fetchFromRemote(rateId: Int) {
        loading.value = true
        disposable.add(
            rateApiService.getRateDetails(rateId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RateDetailsModel>() {
                    override fun onSuccess(rateList: RateDetailsModel) {
                        rate.value = rateList
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        rateLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

}