package com.arturlasok.composeArturApp.interactors

import android.util.Log
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.network.WiadomoscService
import com.arturlasok.composeArturApp.presentation.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUser(
    private val wiadomoscService: WiadomoscService,
    private val appUser: AppUser,
) {

    fun getUserFlow(puid: String, token: String, isNetworkAvailable: Boolean): Flow<AppUser> =
        flow {

            if (isNetworkAvailable) {
                try {

                    val netUser = wiadomoscService.search_user(
                        token = token,
                        puid = puid,

                        )
                    appUser.set_pid(netUser.netpid)
                    appUser.set_pimie(netUser.netpimie)
                    appUser.set_pnazwisko(netUser.npnazwisko)

                    emit(appUser)
                } catch (e: Exception) {
                    Log.i(TAG, "Pobieranie getUserFlow ${e.cause}")
                }
            }
        }
}