package com.arturlasok.composeArturApp.presentation.components.user

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.components.PowrotButton
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.ui.theme.RuchAppArturTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.Executor

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
    if(FirebaseAuth.getInstance().currentUser == null) {
        Log.i(TAG, "Error FB user null")
        navController.popBackStack()}

    var routeforward = "null"
    val loading = false
    val scaffoldState = rememberScaffoldState()




    RuchAppArturTheme(loading, isDarkTheme, isNetworkAvailable, isConMonVis) {
        Scaffold(

            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
            topBar = {
                if(operacja==7) {
                    routeforward = Screen.ListaWiadomosci.route
                    if (navController.previousBackStackEntry?.destination?.route?.startsWith("UserView") == true) {
                        Log.d(TAG, "navController.backQueue.firstOrNull():${navController.backQueue.first().destination.route}" )
                        Log.d(TAG,"navController.currentBackStackEntry.toString():${navController.currentBackStackEntry?.destination?.route}" )
                        Log.d(TAG,"navController.previousBackStackEntry:${navController.previousBackStackEntry?.destination?.route}" )
                        // Log.d(TAG,"navController.enableOnBackPressed(false):${navController.enableOnBackPressed(false)}" )
                        Log.d(TAG,"navController.currentDestination:${navController.currentDestination}")
                        //val rr = navController.previousBackStackEntry?.destination
                       // if (rr != null) {
                        //    navController.currentBackStackEntry?.destination?.parent?.remove(rr)
                        //}


                    }
                }
            //Ustawienia Powrotu
             val routeback = "null"; PowrotButton(navController, routeback, routeforward)

            })
        {
           UserAdminProfile(userAdminViewModel)
        }
    }
}

