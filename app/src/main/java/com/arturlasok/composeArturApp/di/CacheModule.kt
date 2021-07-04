package com.arturlasok.composeArturApp.di

import androidx.room.Room
import com.arturlasok.composeArturApp.BaseApplication
import com.arturlasok.composeArturApp.cache.WiadomoscDao
import com.arturlasok.composeArturApp.cache.database.AppDatabase
import com.arturlasok.composeArturApp.cache.model.WiadomoscEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWiadomoscDao(db: AppDatabase): WiadomoscDao {
        return db.wiadomoscDao()
    }

    @Singleton
    @Provides
    fun provideCacheWiadomoscMapper(): WiadomoscEntityMapper {
        return WiadomoscEntityMapper()
    }

}
