package com.arturlasok.composeArturApp.presentation.components.user

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.components.PowrotButton
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.ui.theme.RuchAppArturTheme
import com.google.firebase.auth.FirebaseAuth

@ExperimentalComposeUiApi
@Composable
fun UserView(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    isConMonVis:Boolean,
    navController: NavController,
    userViewViewModel: UserViewViewModel,
    operacja: Int,


    ) {
    val scope = rememberCoroutineScope()
    var routeforward : String = "null"
    val loading = userViewViewModel.loading.value
    val scaffoldState = rememberScaffoldState()
    val routeback = "null"
    // Wyjscie z aktywnosci jezeli jest zalogowany uzytkownik
    if(FirebaseAuth.getInstance().currentUser != null) { navController.popBackStack()}

    RuchAppArturTheme(loading, isDarkTheme, isNetworkAvailable, isConMonVis) {
        Scaffold(

            scaffoldState = scaffoldState,

            topBar = {

              //Ustawienia nawigacji w top
              if(operacja==1) {
               routeforward = Screen.UserView.route+"/2" }
               PowrotButton(navController,routeback,routeforward)

            },
        ) {

            if(operacja==1) {
                //Login
                UserLogin(navController = navController, scaffoldState= scaffoldState,scope=scope,userViewViewModel = userViewViewModel)

            }
            if(operacja==2) {
                //Rejestracja
                UserRegister(navController = navController, scaffoldState = scaffoldState,scope = scope,userViewViewModel = userViewViewModel)

            }
            if(operacja==3) {
                //Odzysk hasla
                UserPass(navController = navController, scaffoldState = scaffoldState,scope = scope,userViewViewModel = userViewViewModel)

            }
            if(operacja==4) {
                //Odzysk hasla odpowiedz po wyslaniu
                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Icon(Icons.Outlined.AccountCircle, contentDescription = "Login screen")
                    Text(
                        modifier = Modifier.padding(top = 40.dp, bottom = 40.dp),
                        text = "ODZYSKIWANIE HASŁA",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp, bottom = 20.dp,start = 10.dp, end=10.dp),
                        textAlign = TextAlign.Center,
                        text="Link umożliwiający restart hasła został wysłany na twoją skrzynkę mailową")
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(Color.DarkGray),
                        enabled = true,
                        onClick = {
                            val route = Screen.UserView.route + "/1"
                            navController.navigate(route)
                        },
                    ) {
                        Text("ZALOGUJ SIĘ")
                    }
                }

            }
        }
    }
}




