package com.arturlasok.composeArturApp.presentation.components
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ListaWiadomosciTopAppBar(
    navController: NavController,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    drawerState: DrawerState,
    gestureEnable: MutableState<Boolean>
    )
{
        Surface(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            //color = MaterialTheme.colors.surface,
            elevation = 8.dp,




        ) {
           Row(horizontalArrangement = Arrangement.Start,verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier.fillMaxWidth().padding(start = 12.dp,end= 12.dp)) {



               Column(modifier = Modifier.wrapContentWidth(Alignment.Start).weight(8f)) {

                    Text(text = "Compose App",color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.h5)


                }

               Column(modifier = Modifier.wrapContentWidth(Alignment.End).weight(2f)) {

                  Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                      IconButton(
                          onClick = {
                              if(FirebaseAuth.getInstance().currentUser != null) {

                                  scope.launch { scaffoldState.drawerState.open();
                                       }
                          } else {
                                  val route = Screen.UserView.route+"/1"
                                  navController.navigate(route)
                          }
                          }
                      ) {
                          Icon(Icons.Filled.Person, contentDescription = "User")
                      }

                      IconButton(
                          onClick = {
                              val route = Screen.SettingsView.route
                              navController.navigate(route)
                          }
                      ) {
                          Icon(Icons.Filled.Settings, contentDescription = "Ustawienia")
                      }
                  }

               }




            }

        }
}