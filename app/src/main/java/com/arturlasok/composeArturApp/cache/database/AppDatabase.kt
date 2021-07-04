package com.arturlasok.composeArturApp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arturlasok.composeArturApp.cache.WiadomoscDao
import com.arturlasok.composeArturApp.cache.model.WiadomoscEntity

@Database(entities = [WiadomoscEntity::class ], version = 1,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun wiadomoscDao(): WiadomoscDao

    companion object{
        val DATABASE_NAME: String = "rr_db"
    }


}