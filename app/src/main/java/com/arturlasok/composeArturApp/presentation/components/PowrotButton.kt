package com.arturlasok.composeArturApp.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG

@Composable
fun PowrotButton(navController: NavController,routeback: String, routeforward: String) {


    Surface(
        modifier = Modifier.fillMaxWidth().height(56.dp),
        //color = MaterialTheme.colors.surface,
        elevation = 8.dp,




        ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)) {



            Row(
                modifier = Modifier
                    .clickable(

                        onClick = {
                            if(routeback!="null") {
                            navController.navigate(routeback)
                                Log.i(TAG, "Route Back: $routeback")
                                  } else {
                                Log.i(TAG, "Route Back: NavigateUp")
                                      navController.navigateUp() } }
                    )
            ) {

                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Przejdź do poprzedniej aktywności",
                )



                Text(
                    text = "Powrót", color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.h5,modifier = Modifier.padding(start=10.dp)
                )
            }
            if(routeforward!="null") {
               Row(
                    modifier = Modifier
                        .clickable(

                            onClick = {
                                    try {
                                        navController.navigate(routeforward)

                                    } catch (e:Exception) {  Log.d(TAG, "nav exb: $e")}
                            }
                        )
                ) {
                    Text(
                        text = "Rejestracja",
                        color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = "Przejdź do kolejnej aktywności",
                    )




                }
            }
        }
    }





}