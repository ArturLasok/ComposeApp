package com.arturlasok.composeArturApp.presentation.components.user

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.components.ListaWiadomosciViewModel
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.google.accompanist.coil.rememberCoilPainter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ruchradzionkow.ruchappartur.R



@Composable
fun UserDrawer(
    navController : NavController,
    gestureEnable: MutableState<Boolean>,
    listaWiadomosciViewModel: ListaWiadomosciViewModel,
    drawerState: DrawerState,
    scope:CoroutineScope,
    scaffoldState: ScaffoldState
               ) {



        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
            Text(modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.h5,
                text = "Cześć ${ listaWiadomosciViewModel.getUserData().getValue("imie").value}")
            Icon(
                Icons.Filled.AccountBox,
                contentDescription = "Twój profil",
                modifier = Modifier
                    .size(130.dp)
                    .clickable(true,onClick = { navController.navigate(Screen.UserAdmin.route+"/9")} )


                ,tint = MaterialTheme.colors.onBackground
            )
            Text(modifier = Modifier.padding(4.dp),
                text = "e-mail: ${ listaWiadomosciViewModel.getUserData().getValue("mail").value}")
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onBackground)
                    .height(1.dp)
            )
            Text(modifier = Modifier.padding(4.dp),style = MaterialTheme.typography.h5,
                text = "Dodaj Wiadomość")
            Text(modifier = Modifier.padding(4.dp),style = MaterialTheme.typography.h5,
                text = "Usuń Wiadomość")
            Text(modifier = Modifier.padding(4.dp),style = MaterialTheme.typography.h5,
                text = "Twoje Wiadomości (5)")
            Text(modifier = Modifier.padding(4.dp),style = MaterialTheme.typography.h5,
                text = "Polubione Wiadomości (3)")
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onBackground)
                    .height(1.dp)
            )

            Row() {
               Button(
                   colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground),
                   modifier = Modifier

                       .padding(top = 16.dp,end = 10.dp),
                   onClick = {


                       navController.navigate(Screen.UserAdmin.route+"/9")
                       },
                   content = { Text(color = Color.White, text ="EDYTUJ PROFIL") }
               )
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground),
                    modifier = Modifier

                        .padding(top = 16.dp,end = 10.dp),
                    onClick = {
                        gestureEnable.value = false
                        listaWiadomosciViewModel.getUserIn().reset_appUser()
                        FirebaseAuth.getInstance().signOut()
                        scope.launch {



                            scaffoldState.drawerState.close()

                        }
                       val route = Screen.ListaWiadomosci.route
                       navController.navigate(route) {
                          popUpTo(Screen.ListaWiadomosci.route) { Log.d(TAG, "NAVIGATION!!!!")
                              } }


                              },
                    content = { Text(color = Color.White, text ="WYLOGUJ") }
                )

           }










    }

}
