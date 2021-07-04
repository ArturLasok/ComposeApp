package com.arturlasok.composeArturApp.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.ui.theme.RuchAppArturTheme

@Composable
fun SzczegolyWiadomosci(
    navController: NavController,
    szczegolyWiadomosciViewModel: SzczegolyWiadomosciViewModel,
    isNetworkAvailable : Boolean,
    isConMonVis :Boolean,
    wiadomoscId: Int,
    isDarkTheme: Boolean
) {
    val loading = szczegolyWiadomosciViewModel.loading.value
    val scaffoldState = rememberScaffoldState()
    val wiadomosc = szczegolyWiadomosciViewModel.wiadomosc.value
    if (wiadomosc != null) {  Log.i(TAG, "no null") }else {

        Log.i(TAG, "null")
            szczegolyWiadomosciViewModel.onTriggerEvent(
                SzczegolyWiadomosciEvent.GetWiadomoscEvent(
                    wiadomoscId
                )
            )

    }
    RuchAppArturTheme(loading, isDarkTheme,isNetworkAvailable,isConMonVis) {
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {scaffoldState.snackbarHostState},
            topBar = {
                     val routeback = Screen.ListaWiadomosci.route
                     PowrotButton(navController,routeback,"null")

            },
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                    if (wiadomosc != null) {
                        WiadomoscView(wiadomosc_vm = wiadomosc)
                    }

            }
        }

    }
}