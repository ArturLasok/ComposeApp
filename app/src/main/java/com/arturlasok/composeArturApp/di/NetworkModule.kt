package com.arturlasok.composeArturApp.di


import com.google.gson.GsonBuilder
import com.arturlasok.composeArturApp.network.WiadomoscService
import com.arturlasok.composeArturApp.network.model.WiadomoscDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideWiadomoscMapper(): WiadomoscDtoMapper
    {
        return WiadomoscDtoMapper()
    }

    @Singleton
    @Provides
    fun provideWiadomoscService(): WiadomoscService
    {
        return Retrofit.Builder()
            .baseUrl("http://arcz.ddns.net/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WiadomoscService::class.java)

    }
    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken() : String {
        return  "tokenstring"
    }


}