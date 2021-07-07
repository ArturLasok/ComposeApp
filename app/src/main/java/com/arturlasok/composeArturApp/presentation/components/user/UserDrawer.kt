package com.arturlasok.composeArturApp.presentation.components.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.google.accompanist.coil.rememberCoilPainter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import ruchradzionkow.ruchappartur.R


@ExperimentalMaterialApi
@Composable
fun UserDrawer(
    navController : NavController,
    gestureEnable: MutableState<Boolean>,
    listaWiadomosciViewModel: ListaWiadomosciViewModel
               ) {



        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()) {
            Text(modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.h5,
                text = "Cześć ${ listaWiadomosciViewModel.getUserData().getValue("imie")}")
            Icon(
                Icons.Filled.AccountBox,
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .clickable(true,onClick = { navController.navigate(Screen.UserAdmin.route+"/9")} )


                ,tint = MaterialTheme.colors.onBackground
            )
            Text(modifier = Modifier.padding(4.dp),
                text = "e-mail: ${ listaWiadomosciViewModel.getUserData().getValue("mail")}")
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
                       gestureEnable.value = false
                       FirebaseAuth.getInstance().signOut()
                       val route = Screen.ListaWiadomosci.route
                       navController.navigate(route) },
                   content = { Text(color = Color.White, text ="WYLOGUJ") }
               )

           }










    }

}
