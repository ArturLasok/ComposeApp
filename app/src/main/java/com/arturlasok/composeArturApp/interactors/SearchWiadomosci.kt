package com.arturlasok.composeArturApp.interactors

import android.util.Log
import com.arturlasok.composeArturApp.cache.WiadomoscDao
import com.arturlasok.composeArturApp.cache.model.WiadomoscEntityMapper
import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.network.WiadomoscService
import com.arturlasok.composeArturApp.network.model.WiadomoscDtoMapper
import com.arturlasok.composeArturApp.presentation.util.RECIPE_PAGINATION_PAGE_SIZE
import com.arturlasok.composeArturApp.presentation.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchWiadomosci(
    private val wiadomoscDao: WiadomoscDao,
    private val wiadomoscService: WiadomoscService,
    private val entityMapper: WiadomoscEntityMapper,
    private val dtoMapper: WiadomoscDtoMapper

)
{
    fun clearDataBaseFlow() : Flow<String> = flow {
        wiadomoscDao.deleteAllWiadomosci()
        emit("DataCleared")
    }
    fun poszukajWszystkichFlow(token:String ,page: Int,isNetworkAvailable: Boolean) : Flow<List<Wiadomosc>> = flow {


        if(isNetworkAvailable){
            try {
                val list = dtoMapper.fromDtoToDomainList(
                    wiadomoscService.search(
                        token = token,
                        page= page,
                        ""
                    ).wszystkiewiadomosci
                )
                wiadomoscDao.insertWiadomosci(entityMapper.fromDomainToEntityList(list))
                Log.i(TAG, "net ok, pobieranie z netu i ladowanie do cache")
                emit(list)


            } catch (e: Exception) {

                Log.i(TAG, "blad retrofit, pobieranie z cache")
                val list = entityMapper.fromEntityToDomainList(
                    wiadomoscDao.getAllWiadomosci(
                    page = page,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE
                    )
                )
                emit(list)
            }


        } else {
            try {
                Log.i(TAG, "net brak, pobieranie z cache")
                val list = entityMapper.fromEntityToDomainList(
                    wiadomoscDao.getAllWiadomosci(
                    page = page,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE
                    )
                )
                emit(list)

                delay(3000)

                try {
                    val list = dtoMapper.fromDtoToDomainList(
                        wiadomoscService.search(
                            token = token,
                            page= page,
                            ""
                        ).wszystkiewiadomosci
                    )
                    wiadomoscDao.insertWiadomosci(entityMapper.fromDomainToEntityList(list))
                    Log.i(TAG, "net po delay ok, pobieranie z netu i ladowanie do cache")
                    emit(list)


                } catch (e: Exception) {   Log.i(TAG, "Dalej lipa z netem") }







            } catch (e: Exception) {   Log.i(TAG, "blad danych w cache") }

        }






    }

}