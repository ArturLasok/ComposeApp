package com.arturlasok.composeArturApp.interactors

import android.util.Log
import com.arturlasok.composeArturApp.cache.WiadomoscDao
import com.arturlasok.composeArturApp.cache.model.WiadomoscEntityMapper
import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.network.WiadomoscService
import com.arturlasok.composeArturApp.network.model.WiadomoscDtoMapper
import com.arturlasok.composeArturApp.presentation.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWiadomosc(
    private val wiadomoscDao: WiadomoscDao,
    private val wiadomoscService: WiadomoscService,
    private val entityMapper: WiadomoscEntityMapper,
    private val dtoMapper: WiadomoscDtoMapper

) {
    fun jednaWiadomoscFlow(id: Int, token: String, isNetworkAvailable: Boolean): Flow<Wiadomosc> =
        flow {
            try {
                var wiadomosc = getWiadomoscFromCache(wiadomoscId = id)
                if (wiadomosc != null) {
                    emit(wiadomosc)
                }

            } catch (e: Exception) {
                Log.i(TAG, "launchJob: Exception: {$e}, {${e.cause}")
            }
        }

    private  suspend fun getWiadomoscFromCache(wiadomoscId: Int): Wiadomosc? {
        return wiadomoscDao.getWiadomoscById(wiadomoscId)?.let { wiadomoscEntity ->
            entityMapper.mapToDomainModel(wiadomoscEntity)
        }
    }
}