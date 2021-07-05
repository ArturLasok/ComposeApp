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
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserAdmin(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    isConMonVis:Boolean,
    navController: NavController,
    userAdminViewModel: UserAdminViewModel,
    operacja: Int,

    ) {
    // Wyjscie z aktywnosci jezeli jest zalogowany uzytkownik
    if(FirebaseAuth.getInstance().currentUser == null) { navController.popBackStack()}

    val routeforward = "null"
    val loading = false
    val scaffoldState = rememberScaffoldState()

    userAdminViewModel.setFirebaseLang()

    RuchAppArturTheme(loading, isDarkTheme, isNetworkAvailable, isConMonVis) {
        Scaffold(

            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
            topBar = {

                //Ustawienia Powrotu
                val routeback = "null"; PowrotButton(navController, routeback, routeforward)

            })
        {
           UserAdminProfile()
        }
    }
}

