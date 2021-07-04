package com.arturlasok.composeArturApp.presentation.components.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.components.PowrotButton
import com.arturlasok.composeArturApp.presentation.components.WiadomoscView
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.ui.theme.RuchAppArturTheme

@Composable
fun SettingsView(
    isDarkTheme : Boolean,
    isNetworkAvailable :Boolean,
    isConMonVis :Boolean,
    navController: NavController,
    onToggleTheme: () -> Unit,
){



    val loading = false
    val scaffoldState = rememberScaffoldState()

    RuchAppArturTheme(loading, isDarkTheme,isNetworkAvailable,isConMonVis) {
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {scaffoldState.snackbarHostState},
            topBar = {
                val routeback = Screen.ListaWiadomosci.route
                PowrotButton(navController,routeback,"null")

            },
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(),) {
                    Text(
                        "Dostosuj ustawienia aplikacji",
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    Text(
                        "Włącz ciemny motyw aplikacji",
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.primary
                    )
                    val checkedState = remember { mutableStateOf(isDarkTheme) }
                    Switch(
                        modifier = Modifier.height(40.dp),
                        checked = isDarkTheme,
                        onCheckedChange = { checkedState.value = it; onToggleTheme() }
                    )

                }

            }

        }

    }

}