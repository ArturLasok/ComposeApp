package com.arturlasok.composeArturApp.presentation.components


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.interactors.GetUser
import com.arturlasok.composeArturApp.interactors.SearchWiadomosci
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.presentation.util.isOnline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30
const val STATE_KEY_PAGE = "wiadomosc.state.page.key"
const val STATE_KEY_LIST_POSITION = "wiadomosc.state.query.list_position"


@HiltViewModel
class ListaWiadomosciViewModel @Inject constructor(
    private val getUser: GetUser,
    private val appUser: AppUser,
    private val searchWiadomosci: SearchWiadomosci,
    private val connectivityManager: isOnline,
    private @Named("auth_token") val token: String,
    private val savedStateHandle: SavedStateHandle

): ViewModel() {


    fun getUserData() = appUser.getUserData()
    fun getUserIn() = appUser
    val gestureEnable = mutableStateOf(false)
    val wiadomosci: MutableState<List<Wiadomosc>> = mutableStateOf(ArrayList())
    val page = mutableStateOf(1)
    var wiadomosciScrollPosition = 0
    val loading = mutableStateOf(false)
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
       // clearDataBase()
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }
        onTriggerEvent(ListaWiadomosciEvent.NewSearchEvent)
        onTriggerEvent(ListaWiadomosciEvent.UserProfileUpdate)
    }
    fun testDrawerGesture() {
        // Gesture Drawer Enable
        if(appUser.get_puid() != null) {
            gestureEnable.value = true }
    }

    fun onTriggerEvent(event: ListaWiadomosciEvent)
    {
        viewModelScope.launch {

            _isRefreshing.emit(true)
            try {

                when(event){
                    is ListaWiadomosciEvent.NewSearchEvent -> { NewSearch() }
                    is ListaWiadomosciEvent.NextPageEvent -> { NextPage() }
                    is ListaWiadomosciEvent.UserProfileUpdate -> { UserAdminProfileUpdate()}
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

    fun UserAdminProfileUpdate() {

        appUser.get_puid()?.let {
            getUser.getUserFlow(
                token = token,
                puid = it,
                isNetworkAvailable = connectivityManager.isNetworkAvailable.value
            ).onEach { data ->


                Log.i(TAG, "Load: ${data}")

                loading.value = false
            }.launchIn(viewModelScope)
        }




    }


    //Czyszczenie bazy danych z wiadomosciami
    fun clearDataBase() {
        searchWiadomosci.clearDataBaseFlow().onEach { data ->
            Log.i(TAG, "BAZA: $data")
        }.launchIn(viewModelScope)
    }

    //Nowe wyszukiwanie wszystkich wiadomosci
    fun NewSearch() {
        loading.value = true
        resetSearchState()

        searchWiadomosci.poszukajWszystkichFlow(
            token = token,
            page = page.value,
            connectivityManager.isNetworkAvailable.value
        ).onEach { data ->
            // Jezeli brak dancyh to blad
            if(data.isEmpty()) {   Log.i(TAG, "empty data in new serach")}
            else {
            // Zapis wiadomosci data do viewmodel wiadomosci
            wiadomosci.value = data


            }

            _isRefreshing.emit(false)
            loading.value = false
        }.launchIn(viewModelScope)



    }
    fun NextPage() {
        if ((wiadomosciScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            incrementPage()
            if (page.value > 1) {
                searchWiadomosci.poszukajWszystkichFlow(
                    token = token,
                    page = page.value,
                    connectivityManager.isNetworkAvailable.value
                ).onEach { data ->
                    // Jezeli brak dancyh to blad
                    if(data.isEmpty()) {   Log.i(TAG, "empty data in new serach")}
                    else {
                        // Zapis dodatkowych wiadomosci data do viewmodel wiadomosci
                        appendWiadomosci(data)

                        }
                    _isRefreshing.emit(false)
                }.launchIn(viewModelScope)
            }

        }
    }
    private fun appendWiadomosci(wiadomosci: List<Wiadomosc>){
        val current = ArrayList(this.wiadomosci.value)
        current.addAll(wiadomosci)
        this.wiadomosci.value = current
    }
    private fun resetSearchState() {
        wiadomosci.value = listOf()
        page.value = 1
        onChangeWiadomosciScrollPosition(0)

    }
    fun onChangeWiadomosciScrollPosition(position: Int){
        setListScrollPosition(position = position)
    }
    private fun setListScrollPosition(position: Int){
        wiadomosciScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }
    private fun incrementPage(){
        setPage(page.value + 1)
    }
}