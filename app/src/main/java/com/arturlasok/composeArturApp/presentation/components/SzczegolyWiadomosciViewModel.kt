package com.arturlasok.composeArturApp.presentation.components

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.interactors.GetWiadomosc

import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.presentation.util.isOnline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class SzczegolyWiadomosciViewModel @Inject constructor(
    private val getWiadomosc: GetWiadomosc,
    private val connectivityManager: isOnline,
    private @Named("auth_token") val token: String,
    private val state: SavedStateHandle,
): ViewModel (){

    val wiadomosc: MutableState<Wiadomosc?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    fun onTriggerEvent(event: SzczegolyWiadomosciEvent)
    {
        viewModelScope.launch {


            try {

                when(event){
                    is SzczegolyWiadomosciEvent.GetWiadomoscEvent -> {

                            getWiadomosc(event.id)


                    }
                }
            }
            catch (e:Exception) {
                Log.i(TAG, "launchJob: Exception: {$e}, {${e.cause}")
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")

            }
        }
    }

    private fun getWiadomosc(id: Int){

            loading.value = true

            getWiadomosc.jednaWiadomoscFlow(
                id = id,
                token = token,
                isNetworkAvailable = connectivityManager.isNetworkAvailable.value
            ).onEach { data ->
                // Jezeli brak dancyh to blad
                if(data.domtytul?.isNotEmpty() == true)
                {
                    // Zapis wiadomosci data do viewmodel wiadomosci
                    wiadomosc.value = data
                }else  {   Log.i(TAG, "empty data in get wiadomosc")}
                loading.value = false
        }.launchIn(viewModelScope)
    }
}


