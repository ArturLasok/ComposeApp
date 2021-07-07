package com.arturlasok.composeArturApp.presentation.components

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.components.user.UserDrawer
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.ui.theme.RuchAppArturTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun ListaWiadomosci(

    navController:NavController,
    listaWiadomosciviewModel: ListaWiadomosciViewModel,
    isNetworkAvailable: Boolean,
    isConMonVis: Boolean,
    isDarkTheme: Boolean,

    ) {
    val wiadomosci = listaWiadomosciviewModel.wiadomosci.value
    val page = listaWiadomosciviewModel.page.value
    val loading = listaWiadomosciviewModel.loading.value


    RuchAppArturTheme(
        displayProgressBar = loading,
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        isConMonVis = isConMonVis,
    ) {
        val drawerState = rememberDrawerState(DrawerValue.Open)

        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        val drawerBackgroundColorSet : Color = if(isDarkTheme) { Color.Black}
                                                else { Color.LightGray}



            Scaffold(
                scaffoldState = scaffoldState,
                drawerContent =  {     UserDrawer(navController,listaWiadomosciviewModel.gestureEnable,listaWiadomosciviewModel)          },
                drawerGesturesEnabled = listaWiadomosciviewModel.gestureEnable.value,
                drawerShape = MaterialTheme.shapes.medium,
                drawerElevation = DrawerDefaults.Elevation,
                drawerBackgroundColor = drawerBackgroundColorSet,
                //drawerContentColor = contentColorFor(drawerBackgroundColor),
                //drawerScrimColor = DrawerDefaults.scrimColor,
                topBar = {


                    ListaWiadomosciTopAppBar(
                        navController = navController,
                        scope = scope,
                        scaffoldState = scaffoldState,
                        drawerState = drawerState,
                        gestureEnable = listaWiadomosciviewModel.gestureEnable

                    )
                },
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        text = { Text("Dodaj") },
                        onClick = {
                            listaWiadomosciviewModel.gestureEnable.value = true
                        }
                    )
                },
                content = {

                     listaWiadomosciviewModel.testDrawerGesture()
                    Log.d(TAG, "gestureEnable in ListaWiadomosci: ${listaWiadomosciviewModel.gestureEnable.value}")
                        ListaWiadomosciLazyCol(
                            wiadomosci = wiadomosci,
                            onChangeScrollPosition = listaWiadomosciviewModel::onChangeWiadomosciScrollPosition,
                            page = page,
                            onTriggerNextPage = {
                                listaWiadomosciviewModel.onTriggerEvent(
                                    ListaWiadomosciEvent.NextPageEvent
                                )
                            },
                            onnavController = navController,
                            viewModeltoSwipe = listaWiadomosciviewModel
                        )

                    }


            )

    }
}