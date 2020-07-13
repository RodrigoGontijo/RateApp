package com.example.rateapp.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.example.rateapp.RateConstants.Companion.BASE_URL
import com.example.rateapp.model.raterepository.RateDatabase
import com.example.rateapp.model.raterepository.api.RateApi
import com.example.rateapp.model.raterepository.dao.RateDao
import com.example.rateapp.model.raterepository.service.RateApiService
import com.example.rateapp.notification.NotificationsHelper
import com.example.rateapp.util.TimeCacheUtil
import com.example.rateapp.viewmodel.RateDetailsViewModel
import com.example.rateapp.viewmodel.RateListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Modules {

    private const val PREF_TIME = "PrefsTime"

    private fun provideTimePreferences(app: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(app.applicationContext)

    private val viewModelModule = module {
        viewModel {
            RateListViewModel(
                get(),
                get() as RateDao,
                get(named("timePrefs")),
                get(),
                get(named("timeCacheUtil"))
            )
        }
        viewModel { RateDetailsViewModel(get()) }
    }

    private val rateApiModule = module {
        single {
            val retrofit: Retrofit = get()
            retrofit.create(RateApi::class.java)
        }

        single {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }

    private val repositoryModule = module {
        single { RateApiService(get()) }
    }

    private val notificationsHelper = module {
        single { NotificationsHelper(get()) }
    }

    private val dbModule = module {

        single {
            Room.databaseBuilder(
                androidContext(), RateDatabase::class.java,
                "ratedatabase"
            ).build()
        }

        single { get<RateDatabase>().rateDao }

    }

    val preferencesModule = module {
        single(named("timePrefs")) { provideTimePreferences(androidApplication()) }
    }

    val timeCacheUtil = module {
        single(named("timeCacheUtil")) { TimeCacheUtil(get(named("timePrefs"))) }
    }


    val all: List<Module> = listOf(
        viewModelModule,
        rateApiModule,
        repositoryModule,
        dbModule,
        preferencesModule,
        notificationsHelper,
        timeCacheUtil
    )

}