package com.arturlasok.composeArturApp.di

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import com.arturlasok.composeArturApp.BaseApplication
import com.arturlasok.composeArturApp.MainActivity
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executor
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