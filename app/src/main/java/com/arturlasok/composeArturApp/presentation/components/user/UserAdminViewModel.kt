package com.arturlasok.composeArturApp.presentation.components.user

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAdminViewModel @Inject constructor(

    ): ViewModel() {

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
    fun UserAdminProfileEdit() {        }
}