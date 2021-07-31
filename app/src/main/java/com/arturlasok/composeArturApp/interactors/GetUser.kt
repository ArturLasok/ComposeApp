package com.arturlasok.composeArturApp.interactors

import android.util.Log
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.network.WiadomoscService
import com.arturlasok.composeArturApp.presentation.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUser(
    private val wiadomoscService: WiadomoscService,
    private val appUser: AppUser,
) {
    fun updateUserToMysqlinAdmin(puid: String,token: String, isNetworkAvailable: Boolean):Flow<Boolean> =
        flow {
            if (isNetworkAvailable) {
                try {

                    val response =
                        wiadomoscService.update_user_to_mysql(
                            token,
                            puid,
                            appUser.get_pimie().value.orEmpty(),
                            appUser.get_pnazwisko().value.orEmpty(),
                            "save"
                        )

                    emit(response.toBoolean())
                } catch (e: Exception) {
                    emit(false)
                    Log.i(TAG, "Service update profile ${e.cause}")
                }



            } else { emit(false) }
        }
   fun putUserToMysqlinAdmin(puid: String,token: String, isNetworkAvailable: Boolean):Flow<Boolean> =
       flow {
           if (isNetworkAvailable) {
               try {

                        val response =
                            wiadomoscService.put_user_to_mysql(token,puid)

                   emit(response.toBoolean())
               } catch (e: Exception) {
                   emit(false)
                   Log.i(TAG, "Service add profile ${e.cause}")
               }



           } else { emit(false) }
       }
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
                    Log.i(TAG, "Service load profil: ${e.cause}")
                }
            }
        }
}