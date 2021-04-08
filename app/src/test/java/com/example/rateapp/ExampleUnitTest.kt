package com.example.rateapp

import RxSchedulerRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.rateapp.model.raterepository.RateDetailsModel
import com.example.rateapp.model.raterepository.service.RateApiService
import com.example.rateapp.viewmodel.RateDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify

import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExtendWith(MockKExtension::class)
class ExampleUnitTest {

    @Rule
    @Suppress("unused")
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerRule()

    @MockK
    lateinit var rateApiService: RateApiService

    @MockK
    lateinit var rateMock: Observer<RateDetailsModel>

    @MockK
    lateinit var loadingMock: Observer<Boolean>

    @MockK
    lateinit var rateLoadErrorMock: Observer<Boolean>

    @InjectMockKs
    lateinit var rateDetailsViewModel: RateDetailsViewModel

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }

    @Test
    fun shouldReturnSucessWhenDetailsRequest(){
        val response = RateDetailsModel("","", 0, "",  "", 0F, null, "")

        every { rateApiService.getRateDetails(1) } answers { Single.just(response)}
        every { rateMock.onChanged(any()) } answers { nothing }
        every { loadingMock.onChanged(any()) } answers { nothing }

        rateDetailsViewModel.rate.observeForever(rateMock)
        rateDetailsViewModel.loading.observeForever(loadingMock)
        rateDetailsViewModel.fetchFromRemote(1)

        verify { loadingMock.onChanged(true) }
        verify { rateMock.onChanged(response) }
        verify { loadingMock.onChanged(false) }
    }

}