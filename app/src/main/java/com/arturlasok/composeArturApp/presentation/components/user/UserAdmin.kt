package com.arturlasok.composeArturApp.presentation.components.user

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.components.PowrotButton
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.ui.theme.RuchAppArturTheme

@Composable
fun UserAdmin(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    isConMonVis:Boolean,
    navController: NavController,
    userAdminViewModel: UserAdminViewModel,
    operacja: Int,

    ) {

    var routeforward: String = "null"
    val loading = false
    val scaffoldState = rememberScaffoldState()
    userAdminViewModel.setFirebaseLang()

    RuchAppArturTheme(loading, isDarkTheme, isNetworkAvailable, isConMonVis) {
        Scaffold(

            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
            topBar = {

                //Ustawienia tobappbarmenu
                when (operacja) {
                    9 -> {
                        val routeback = Screen.ListaWiadomosci.route; PowrotButton(
                            navController,
                            routeback,
                            routeforward
                        )
                    }
                    8 -> {
                        val routeback = Screen.ListaWiadomosci.route; PowrotButton(
                            navController,
                            routeback,
                            routeforward
                        )
                    }

                    else -> {
                        Log.i(TAG, "Route Back:   val routeback = \"null\"; PowrotButton(")
                        val routeback = "null"; PowrotButton(navController, routeback, routeforward)
                    }


                }
            })
        {
            Text("User ADMIN + $operacja");

        }
    }
}

