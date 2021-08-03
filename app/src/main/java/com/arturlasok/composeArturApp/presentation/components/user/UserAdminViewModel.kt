package com.arturlasok.composeArturApp.presentation.components.user

import android.content.Intent
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturlasok.composeArturApp.BaseApplication
import com.arturlasok.composeArturApp.FotoActivity
import com.arturlasok.composeArturApp.Hilt_MainActivity
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.arturlasok.composeArturApp.interactors.GetUser
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.presentation.util.isOnline
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.internal.aggregatedroot.codegen._com_arturlasok_composeArturApp_BaseApplication
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserAdminViewModel @Inject constructor(
    private val appUser: AppUser,
    private @Named("auth_token") val token: String,
    private val connectivityManager: isOnline,
    private val getUser: GetUser,
    private val app: BaseApplication
    ): ViewModel() {
    //klawisz ZAPISZ odblokowanie przy zmianie konfiguracji
    val saveButtonEnable = mutableStateOf(true)
    val isDark = mutableStateOf(false)
    // Dane do formularza edycji w profilu
    val imie_edycja = mutableStateOf("")
    val imie_nowe = mutableStateOf("")
    val nazwisko_edycja = mutableStateOf("")
    val nazwisko_nowe = mutableStateOf("")



   // Loading screen off
    val loading = mutableStateOf(false)
   // inicjacja viewModel
    init {
        setEvent(UserAdminEvent.UserAdminProfileAdd)
        setEvent(UserAdminEvent.UserAdminProfileUpdate)





    }


        //ustawienie jezyka firebase
        fun setFirebaseLang() {
            FirebaseAuth.getInstance().setLanguageCode("pl-PL")
        }

        //events in Admin
        fun setEvent(event: UserAdminEvent) {
            try {

                when (event) {
                   is UserAdminEvent.UserAdminProfileUpdate ->  { UserAdminProfileUpdate() }
                   is UserAdminEvent.UserAdminProfileAdd -> { UserAdminProfileAdd() }
                   is UserAdminEvent.UserAdminProfileSave -> { UserAdminProfileSave() }
                   is UserAdminEvent.UpdateAppUserClass -> { UserAdminProfileUpdateAppUserClass() }
                   is UserAdminEvent.UserAdminFieldsEditUpdate -> {UserAdminFieldsEditUpdate() }
                   is UserAdminEvent.FotoActivityIntent -> { FotoActivityIntent() }


                }
            } catch (e: Exception) {
                Log.d(TAG, "UserAdmin Exception: ${e.cause}")
            }
        }
// EVENT FotoActivity open
fun FotoActivityIntent() {
    try {
        val intent_message = FirebaseAuth.getInstance().currentUser?.uid
        val intent_source = Intent(app.applicationContext, FotoActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, intent_message+">><<"+isDark.value)


        }
        intent_source.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(intent_source)
    } catch (e: Exception) {  Log.d(TAG, "Intent Exception: ${e.cause} ${e.message} ${e.toString()}")}
}
//EVENT Aktualizacja danych do profile fields -----------------------------------------------------
    fun UserAdminFieldsEditUpdate() {
        imie_edycja.value = appUser.get_pimie().value ?:""
        nazwisko_edycja.value = appUser.get_pnazwisko().value ?:""
        imie_nowe.value = imie_edycja.value
        nazwisko_nowe.value = nazwisko_edycja.value

}
//EVENT Aktualizacja klasy AppUser o nowe dane dot imienia i nazwiska w profilu
    fun UserAdminProfileUpdateAppUserClass() {
        appUser.set_pimie(imie_nowe.value)
        appUser.set_pnazwisko(nazwisko_nowe.value)
    }
//EVENT Dodanie uzytkownika do mysql jezeli nie jest zapisany -------------------------------------
    fun UserAdminProfileAdd() {
        loading.value = true

        appUser.get_puid()?.let {
            getUser.putUserToMysqlinAdmin(
                puid = it,
                token = token,
                isNetworkAvailable = connectivityManager.isNetworkAvailable.value
            ).onEach { data ->
                Log.i(TAG, "Add: $data")
                loading.value = false
            }.launchIn(viewModelScope)
        }
    }

//EVENT Zmiana danych w profilu ---------------------------------------------------------
fun UserAdminProfileSave() {
    loading.value = true

    appUser.get_puid()?.let {
        getUser.updateUserToMysqlinAdmin(
            puid = it,
            token = token,
            isNetworkAvailable = connectivityManager.isNetworkAvailable.value
        ).onEach { data ->
            Log.i(TAG, "Update: $data")
            loading.value = false
        }.launchIn(viewModelScope)
    }
}

//EVENT Pobranie danych z profilu  -------------------------------------------------------
        fun UserAdminProfileUpdate() {
            appUser.get_puid()?.let {
                getUser.getUserFlow(
                    token = token,
                    puid = it,
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value
                ).onEach { data ->


                        Log.i(TAG, "Load: ${data}")
                    setEvent(UserAdminEvent.UserAdminFieldsEditUpdate)
                    loading.value = false
                }.launchIn(viewModelScope)
            }




        }
}