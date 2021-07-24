package com.arturlasok.composeArturApp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.arturlasok.composeArturApp.datastore.UstawieniaDataStore
import com.arturlasok.composeArturApp.presentation.components.ListaWiadomosci
import com.arturlasok.composeArturApp.presentation.components.ListaWiadomosciViewModel
import com.arturlasok.composeArturApp.presentation.components.SzczegolyWiadomosci
import com.arturlasok.composeArturApp.presentation.components.SzczegolyWiadomosciViewModel
import com.arturlasok.composeArturApp.presentation.components.settings.SettingsView
import com.arturlasok.composeArturApp.presentation.components.settings.SettingsViewModel
import com.arturlasok.composeArturApp.presentation.components.user.UserAdmin
import com.arturlasok.composeArturApp.presentation.components.user.UserAdminViewModel
import com.arturlasok.composeArturApp.presentation.components.user.UserView
import com.arturlasok.composeArturApp.presentation.components.user.UserViewViewModel
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.presentation.util.isOnline
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ruchradzionkow.ruchappartur.R
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject


// Datastore init
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ustawienia")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    private lateinit var cameraExecutor: Executor

    @Inject
    lateinit var isOnline: isOnline
    @Inject
    lateinit var ustawieniaDataStore: UstawieniaDataStore

    val isConMonVis = mutableStateOf(false)

    override fun onStart() {
       isOnline.runit()
       super.onStart()

    }

    override fun onPause() {
        super.onPause()
        isConMonVis.value = false
    }
    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch() {
            delay(4000)
            isConMonVis.value = true
        }

    }
    override fun onDestroy() {
        super.onDestroy()

        isConMonVis.value = false

    }





    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraExecutor = ContextCompat.getMainExecutor(this)

        setContent {
            Log.d(TAG, "Is network in main activity: ${isOnline.isNetworkAvailable.value}")
            Log.d(TAG, "Is network info visible: ${isConMonVis.value }")

                val systemUiController = rememberSystemUiController()
                ustawieniaDataStore.observeDataStrore(dataStore,systemUiController)
                FirebaseAuth.getInstance().setLanguageCode("pl-PL")


                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.ListaWiadomosci.route) {
                    // NAWIGUJ DO LISTY WIADOMOSCI
                    composable(Screen.ListaWiadomosci.route)
                        { navBackStackEntry ->
                       val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                       val viewModel: ListaWiadomosciViewModel = viewModel("ListaWiadomosciViewModel",factory)

                            ListaWiadomosci(

                                navController = navController,
                                listaWiadomosciviewModel = viewModel,
                                isNetworkAvailable = isOnline.isNetworkAvailable.value,
                                isConMonVis = isConMonVis.value,
                                isDarkTheme = ustawieniaDataStore.isDark.value,


                            )

                    }
                    // NAWIGUJ DO SZCZEGOLY WIADOMOSCI
                    composable(Screen.SzczegolyWiadomosci.route+ "/{wiadomoscId}",
                        arguments = listOf(navArgument("wiadomoscId") {
                        type = NavType.IntType })
                    )
                      { navBackStackEntry ->
                        val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val viewModel: SzczegolyWiadomosciViewModel =
                            viewModel("SzczegolyWiadomosciViewModel", factory)

                          navBackStackEntry.arguments?.let {
                              SzczegolyWiadomosci(
                                  navController = navController,
                                  szczegolyWiadomosciViewModel = viewModel,
                                  isNetworkAvailable = isOnline.isNetworkAvailable.value,
                                  isConMonVis = isConMonVis.value,
                                  wiadomoscId = it.getInt("wiadomoscId"),
                                  isDarkTheme = ustawieniaDataStore.isDark.value
                              )
                          }

                      }
                    // NAWIGUJ DO SETTINGS
                    composable(Screen.SettingsView.route)

                    { navBackStackEntry ->
                        val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val viewModel: SettingsViewModel =
                            viewModel("SettingsViewModel", factory)

                        navBackStackEntry.arguments?.let {
                           SettingsView(
                               isDarkTheme = ustawieniaDataStore.isDark.value,
                               isNetworkAvailable = isOnline.isNetworkAvailable.value,
                               isConMonVis = isConMonVis.value,
                               navController = navController

                           ) {ustawieniaDataStore.UstawDark(dataStore,systemUiController)}
                        }

                    }
                    //Nawgiuj do UserView
                    composable(Screen.UserView.route+ "/{operacja}",
                        arguments = listOf(navArgument("operacja") {
                            type = NavType.IntType }) )

                    { navBackStackEntry ->
                        val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val viewModel: UserViewViewModel =
                            viewModel("UserViewViewModel", factory)

                        navBackStackEntry.arguments?.let {
                            viewModel.passRecButtonEnable.value = true
                            viewModel.loginButtonEnable.value=true
                            viewModel.registerButtonEnable.value=true

                            UserView(
                                isDarkTheme = ustawieniaDataStore.isDark.value,
                                isNetworkAvailable = isOnline.isNetworkAvailable.value,
                                isConMonVis = isConMonVis.value,
                                navController = navController,
                                userViewViewModel = viewModel,
                                operacja = it.getInt("operacja"),

                            )
                        }

                    }
                    //Nawiguj do UserAdmin
                    composable(Screen.UserAdmin.route+ "/{operacja}",
                        arguments = listOf(navArgument("operacja") {
                            type = NavType.IntType }) )

                    { navBackStackEntry ->
                        val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val viewModel: UserAdminViewModel =
                            viewModel("UserAdminViewModel", factory)

                        navBackStackEntry.arguments?.let {

                           UserAdmin(
                                 exec = cameraExecutor,
                                isDarkTheme = ustawieniaDataStore.isDark.value,
                                isNetworkAvailable = isOnline.isNetworkAvailable.value,
                                isConMonVis = isConMonVis.value,
                                navController = navController,
                                userAdminViewModel = viewModel,
                                operacja = it.getInt("operacja"),

                                )
                        }

                    }





                }




        }
    }

}
