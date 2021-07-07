package com.arturlasok.composeArturApp.di

import android.content.Context
import com.arturlasok.composeArturApp.BaseApplication
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule  {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : BaseApplication { return  app as BaseApplication
    }
    @Singleton
    @Provides
    fun provideUser() : AppUser {
       return AppUser()
    }
}