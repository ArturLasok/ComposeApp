package com.arturlasok.composeArturApp.datastore

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.accompanist.systemuicontroller.SystemUiController
import com.arturlasok.composeArturApp.BaseApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UstawieniaDataStore @Inject constructor(app: BaseApplication){



    val DARK_OPT = booleanPreferencesKey("dark_theme_opt")
    val APP_USER_KEY = stringPreferencesKey("null")
    val scope = CoroutineScope(Dispatchers.Main)
    val isDark = mutableStateOf(false)


    // Load from datastore
    fun observeDataStrore(datas: DataStore<Preferences>, systemUiController: SystemUiController) {
        datas.data.onEach { preferences ->
            preferences[DARK_OPT]?.let { isDarkTheme ->
                if(isDarkTheme==false) {
                    systemUiController.setSystemBarsColor(Color(0xFFE7D40B))
                }
                else {  systemUiController.setSystemBarsColor(Color(0xFF33332D))}
                isDark.value = isDarkTheme
            }
        }.launchIn(scope)
    }
    fun UstawDark(datas: DataStore<Preferences>, systemUiController: SystemUiController) {
        scope.launch {
            datas.edit { preferences ->
                val current = preferences[DARK_OPT]?: false

                if(current) {
                    systemUiController.setSystemBarsColor(Color(0xFFE7D40B))
                } else {  systemUiController.setSystemBarsColor(Color(0xFF33332D))}

                preferences[DARK_OPT] = !current

            }
        }

    }
    fun UstawUser(datas: DataStore<Preferences>, firebase: FirebaseAuth) {
        scope.launch {
            datas.edit { preferences ->
                val current = preferences[APP_USER_KEY]?: "null"
                preferences[APP_USER_KEY] = current

            }
        }

    }

}