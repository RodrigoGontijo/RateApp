package com.example.rateapp.model.raterepository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rateapp.model.raterepository.RateModel


@Dao
interface RateDao {
    @Insert
    suspend fun insertAll(vararg rates: RateModel) : List<Long>

    @Query("SELECT * FROM ratemodel")
    suspend fun getAllRates(): List<RateModel>

    @Query("SELECT * FROM ratemodel WHERE id = :rateId")
    suspend fun getRate(rateId: Int) : RateModel

    @Query( "DELETE FROM ratemodel")
    suspend fun deleteAllRates()

}