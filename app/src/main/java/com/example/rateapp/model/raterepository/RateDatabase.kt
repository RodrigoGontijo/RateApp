package com.example.rateapp.model.raterepository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rateapp.model.raterepository.dao.RateDao

@Database(entities = arrayOf(RateModel::class), version = 1)
abstract class RateDatabase : RoomDatabase() {
    abstract val rateDao: RateDao
}