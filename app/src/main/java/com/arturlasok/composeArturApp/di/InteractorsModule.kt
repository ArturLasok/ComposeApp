package com.arturlasok.composeArturApp.di

import com.arturlasok.composeArturApp.cache.WiadomoscDao
import com.arturlasok.composeArturApp.cache.model.WiadomoscEntityMapper
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.arturlasok.composeArturApp.interactors.GetUser
import com.arturlasok.composeArturApp.interactors.GetWiadomosc
import com.arturlasok.composeArturApp.interactors.SearchWiadomosci
import com.arturlasok.composeArturApp.network.WiadomoscService
import com.arturlasok.composeArturApp.network.model.WiadomoscDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {
    @ViewModelScoped
    @Provides
    fun provideGetUser(
        wiadomoscService: WiadomoscService,
        appUser: AppUser
    ) : GetUser {
        return GetUser(
            wiadomoscService = wiadomoscService,
            appUser = appUser,

        )
    }
    @ViewModelScoped
    @Provides
    fun provideSearchWiadomosci(
    wiadomoscService: WiadomoscService,
    wiadomoscDao: WiadomoscDao,
    wiadomoscEntityMapper: WiadomoscEntityMapper,
    wiadomoscDtoMapper: WiadomoscDtoMapper
    ) : SearchWiadomosci {
        return SearchWiadomosci(
            wiadomoscService = wiadomoscService,
            wiadomoscDao = wiadomoscDao,
            entityMapper = wiadomoscEntityMapper,
            dtoMapper = wiadomoscDtoMapper

        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetWiadomosc(
        wiadomoscService: WiadomoscService,
        wiadomoscDao: WiadomoscDao,
        wiadomoscEntityMapper: WiadomoscEntityMapper,
        wiadomoscDtoMapper: WiadomoscDtoMapper
    )  : GetWiadomosc {
        return GetWiadomosc(
        wiadomoscService = wiadomoscService,
        wiadomoscDao = wiadomoscDao,
        entityMapper = wiadomoscEntityMapper,
        dtoMapper = wiadomoscDtoMapper)

    }


}