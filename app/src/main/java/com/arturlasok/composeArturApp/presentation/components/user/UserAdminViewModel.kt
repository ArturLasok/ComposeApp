package com.arturlasok.composeArturApp.presentation.components.user

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.arturlasok.composeArturApp.interactors.GetUser
import com.arturlasok.composeArturApp.interactors.SearchWiadomosci
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.presentation.util.isOnline
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserAdminViewModel @Inject constructor(
    private val appUser: AppUser,
    private @Named("auth_token") val token: String,
    private val connectivityManager: isOnline,
    private val getUser: GetUser
    ): ViewModel() {




    val user_name = mutableStateOf("")
    val loading = mutableStateOf(false)


    init {
        setEvent(UserAdminEvent.UserAdminProfileEdit)
    }
        //ustawienie jezyka firebase
        fun setFirebaseLang() {
            FirebaseAuth.getInstance().setLanguageCode("pl-PL")
        }
        //events in Admin
        fun setEvent(event: UserAdminEvent) {
            try {

                when (event) {
                   is UserAdminEvent.UserAdminProfileEdit ->  { UserAdminProfileEdit() }

                }
            } catch (e: Exception) {
                Log.d(TAG, "UserAdmin Exception: ${e.cause}")
            }
        }
    //Admin profile operacje podczas widoku profilu
    fun UserAdminProfileEdit() {
        loading.value = true

        FirebaseAuth.getInstance().currentUser?.uid?.let {
            getUser.putUserToMysqlinAdmin(
                puid = it,
                token= token,
                isNetworkAvailable = connectivityManager.isNetworkAvailable.value
            ).onEach { data ->
                Log.i(TAG, "Put user response: $data")
                loading.value = false
            }.launchIn(viewModelScope)
        }

/*
        getUser.getUserFlow(
            token = token,
            puid ="111",
            isNetworkAvailable = connectivityManager.isNetworkAvailable.value
        ).onEach { data ->
            // Jezeli brak dancyh to blad
            if(data.get_pimie()==null) {   Log.i(TAG, "User puid is null: ${data.get_pimie()}")}
            else {
                // Zapis wiadomosci data do viewmodel wiadomosci
                Log.i(TAG, "User puid is OK: ${data.get_pimie()}")


            }

            user_name.value = data.get_pimie().toString()
            loading.value = false
        }.launchIn(viewModelScope)

*/


    }
}